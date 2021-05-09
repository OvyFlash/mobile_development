package ua.kpi.comsys.io8130.ThirdFragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import ua.kpi.comsys.io8130.*
import ua.kpi.comsys.io8130.ThirdFragment.Models.Movie
import ua.kpi.comsys.io8130.ThirdFragment.Models.MovieCallback
import ua.kpi.comsys.io8130.ThirdFragment.Models.Search
import ua.kpi.comsys.io8130.ThirdFragment.RecyclerView.CustomItemAnimator
import ua.kpi.comsys.io8130.ThirdFragment.RecyclerView.MovieAdapter
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.coroutines.selects.select
import org.json.JSONException
import org.json.JSONObject
import org.w3c.dom.Text
import ua.kpi.comsys.io8130.FourthFragment.Models.APIResponse
import ua.kpi.comsys.io8130.FourthFragment.Models.Flower
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdFragment : Fragment(), MovieCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var rvMovies: RecyclerView? = null
    private var movieAdapter: MovieAdapter? = null
    private var movies: ArrayList<Movie>? = ArrayList(0)
    private var btnAddMovie: MenuItem? = null
    private var itemsNotFound: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

//        movies = ListViewController.ReadFile(this.requireContext())
//        for (movie in movies!!) {
//            movie.AddFieldByImdbID(this.requireContext())
//        }

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setMovieAdapter()
    }

    private fun initViews() {
        //btnAddMovie = requireView().findViewById<TextView>(R.id.btn_go_to_add_page)
        rvMovies = requireView().findViewById<RecyclerView>(R.id.movie_list)
        rvMovies!!.layoutManager = LinearLayoutManager(this.context)
        rvMovies!!.setHasFixedSize(true)

        rvMovies!!.itemAnimator = CustomItemAnimator()

        itemsNotFound = requireView().findViewById<TextView>(R.id.items_not_found)

        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                this.resources.configuration.isNightModeActive
            } else {
                TODO("VERSION.SDK_INT < R")
            }
        ) {
            itemsNotFound!!.setTextColor(Color.WHITE)
        } else {
            itemsNotFound!!.setTextColor(Color.BLACK)
        }

    }

    private fun setMovieAdapter() {
        movieAdapter = MovieAdapter(movies, this, this.itemsNotFound)
        rvMovies!!.adapter = movieAdapter
        movieAdapter!!.SetItemTouchHelper(rvMovies)

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ThirdFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThirdFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onMovieItemClick(pos: Int, imgContainer: ImageView,
                                  imgMovie: ImageView,
                                  title: TextView,
                                  date: TextView,
                                  type: TextView) {

        val request = AndroidNetworking.get("https://omdbapi.com/")
            .addQueryParameter("apikey", "cb3c0179")
            .addQueryParameter("i", movies!![pos].imdbID)
            .build()
        Log.e("51412", request.url.toString())
        request.getAsJSONObject(object : JSONObjectRequestListener {
            override fun onResponse(response: JSONObject) {
                try {
                    val reader = BufferedReader(
                        InputStreamReader(response.toString().byteInputStream())
                    );

                    try {
                        val r = Gson()
                        val loc = r.fromJson<Search>(reader, Movie::class.java) as Movie
                        movies!![pos] = loc

                    } catch (e: Exception) {
                        Log.e("123", e.toString())
                    }
                } catch (e: JSONException) {
                    Log.e("55551", e.toString())
                }
            }

            override fun onError(error: ANError) {
                // handle error
                Log.e("responce_app", error.toString()) //Logs out 20
            }
        })

        rvMovies!!.adapter!!.notifyDataSetChanged()
        val intent: Intent = Intent(this.requireContext(), MovieDetailedActivity::class.java)
        intent.putExtra("movieObject", movies!![pos])
        val p1: Pair<View,String> = Pair.create(imgContainer, "imageView")
        val p2: Pair<View,String> = Pair.create(imgMovie, "item_movie_img")
        val p3: Pair<View,String> = Pair.create(title, "item_movie_title")
        val p4: Pair<View,String> = Pair.create(date, "item_movie_date")

        val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this.requireActivity(), p1, p2, p3, p4)

        startActivity(intent, optionsCompat.toBundle())

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        val item: MenuItem = menu.findItem(R.id.action_search)
        val searchView: androidx.appcompat.widget.SearchView = item.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length >= 2) {
                    val locMovies = requestMovies(newText)
                    locMovies.forEach {
                        Log.e("2131321", it.Title)
                    }
                    movieAdapter!!.forFiltering = locMovies
                    movieAdapter!!.filter.filter(newText)
                }
                return false
            }

        })

        btnAddMovie = menu.findItem(R.id.action_add)

        return super.onCreateOptionsMenu(menu, inflater)

    }

    private var locMovies: ArrayList<Movie> = ArrayList(0)
    fun requestMovies(text: String): List<Movie> {
        //http://omdbapi.com/?s=Harry%20Potter&apikey=cb3c0179&page=1
        val request = AndroidNetworking.get("https://omdbapi.com/")
            .addQueryParameter("apikey", "cb3c0179")
            .addQueryParameter("s", text)
            .addQueryParameter("page", "1")
            .build()
        Log.e("51412", request.url.toString())
            request.getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    try {
                        val reader = BufferedReader(
                            InputStreamReader(response.toString().byteInputStream())
                        );

                        try {
                            val r = Gson()
                            val loc = r.fromJson<Search>(reader, Search::class.java).Search
                            loc.forEach {
                                locMovies.add(it)
                            }

                        } catch (e: Exception) {
                            Log.e("123", e.toString())
                        }
                    } catch (e: JSONException) {
                        Log.e("55551", e.toString())
                    }
                }

                override fun onError(error: ANError) {
                    // handle error
                    Log.e("responce_app", error.toString()) //Logs out 20
                }
            })
        //Log.e("123", locMovies.toString())

        return locMovies as List<Movie>
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            btnAddMovie!!.itemId -> {
                val intent: Intent = Intent(this.requireContext(), AddMovieActivity::class.java)
                startActivityForResult(intent, 1)
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) {
            return
        }
        val movie: Movie = data.extras!!.getSerializable("newMovie") as Movie
        addMovie(movie)

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun addMovie(movie: Movie) {
        this.movies!!.clear()
        this.movies!!.add(movie)
        this.movies!!.addAll(movieAdapter!!.currentMovies)
        movieAdapter!!.currentMovies = movies
    }
//
//    class ListViewController: Fragment() {
//        companion object {
//            fun ReadFile(context: Context): ArrayList<Movie> {
//
//                var reader: BufferedReader? = null;
//                val search  = Gson()
//
//                try {
//                    reader = BufferedReader(
//                        InputStreamReader(context.assets.open("MoviesList.txt"))
//                    );
//
//                    return search.fromJson<Search>(reader, Search::class.java).Search as ArrayList<Movie>
//                } catch (e: IOException) {
//                    Log.println(Log.INFO, "12", e.toString())
//
//                } finally {
//                    if (reader != null) {
//                        try {
//                            reader.close();
//                        } catch (e: IOException) {
//                            Log.println(Log.INFO, "13", e.toString())
//                        }
//                    }
//                }
//                return search.fromJson<Search>(reader, Search::class.java).Search as ArrayList<Movie>
//            }
//        }
//    }
}
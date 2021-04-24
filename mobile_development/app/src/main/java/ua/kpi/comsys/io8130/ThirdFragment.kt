package ua.kpi.comsys.io8130

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var rvMovies: RecyclerView? = null
    private var movieAdapter: MovieAdapter? = null
    private var movies: List<Movie>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
       movies = ListViewController.ReadFile(this.requireContext())
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
        rvMovies = requireView().findViewById<RecyclerView>(R.id.movie_list)
        rvMovies!!.layoutManager = LinearLayoutManager(this.context)
        rvMovies!!.setHasFixedSize(true)
    }

    private fun setMovieAdapter() {
        movieAdapter = MovieAdapter(movies)
        rvMovies!!.adapter = movieAdapter
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

    class ListViewController: Fragment() {
        companion object {
            fun ReadFile(context: Context): List<Movie> {

                var reader: BufferedReader? = null;
                val search  = Gson()

                try {
                    reader = BufferedReader(
                             InputStreamReader(context.assets.open("MoviesList.txt"))
                    );

                    return search.fromJson<Search>(reader, Search::class.java).Search
                } catch (e: IOException) {
                    Log.println(Log.INFO, "12", e.toString())

                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (e: IOException) {
                            Log.println(Log.INFO, "13", e.toString())
                        }
                    }
                }
                return search.fromJson<Search>(reader, Search::class.java).Search;
            }
        }

    }
}
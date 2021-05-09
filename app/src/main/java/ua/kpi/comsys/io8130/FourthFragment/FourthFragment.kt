package ua.kpi.comsys.io8130.FourthFragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.arasthel.spannedgridlayoutmanager.SpanSize
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONException
import org.json.JSONObject
import ua.kpi.comsys.io8130.FourthFragment.Models.APIResponse
import ua.kpi.comsys.io8130.FourthFragment.Models.Flower
import ua.kpi.comsys.io8130.LoadingIndicator
import ua.kpi.comsys.io8130.R
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FourthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FourthFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var btnAddPhoto: MenuItem? = null
    private final val IMAGE_PICK_CODE: Int = 1000;
    private final val PERMISSION_CODE: Int = 1001;

   // var testImageView: ImageView? = null
    private var images: ArrayList<Flower> = ArrayList(0)
    private var photoAdapter: PhotoAdapter? = null
    private var recyclerImages: RecyclerView? = null
    private var loadingIndicator: LoadingIndicator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        if (savedInstanceState != null) {
            images = savedInstanceState.getParcelableArrayList<Flower>("images") as ArrayList<Flower>
        }
        setHasOptionsMenu(true)
        photoAdapter = PhotoAdapter(this.requireContext(), images)

        requestPhotos()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("images", images)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fourth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*SET HERE RECYCLER AND ADAPTER*/
        recyclerImages = requireView().findViewById<RecyclerView>(R.id.photo_recycler_view)

        val gridLayout: GridLayoutManager = GridLayoutManager(this.context, 3)
        gridLayout.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if ((position % 9) % 6 == 0) {
                    2
                } else 1
            }
        }
        val spanned = SpannedGridLayoutManager(SpannedGridLayoutManager.Orientation.VERTICAL, 3)
        spanned.spanSizeLookup = SpannedGridLayoutManager.SpanSizeLookup { position ->
            if ((position % 9) % 7 == 0) {
                SpanSize(2, 2)
            } else SpanSize(1, 1)
        }

        //staggered.gapStrategy
        recyclerImages!!.layoutManager = spanned
        recyclerImages!!.setHasFixedSize(true)
        recyclerImages!!.adapter = photoAdapter
        /*FINISH SETTINGS*/
        loadingIndicator = LoadingIndicator(this.requireActivity().findViewById<ProgressBar>(R.id.loadingIndicatorRecycler))
        if (images.isEmpty()) {
            loadingIndicator!!.setVisible()
        }
    }

    fun requestPhotos() {
        val request = AndroidNetworking.get("https://pixabay.com/api/")
                .addQueryParameter("key", "21530378-84be28a0fd3ac3f0a943074d0")
                .addQueryParameter("q", "yellow+flowers")
                .addQueryParameter("image_type", "photo")
                .addQueryParameter("per_page", "27")
                .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    try {
                        val reader = BufferedReader(
                            InputStreamReader(response.toString().byteInputStream())
                        );
                        val r = Gson()
                        val resp = r.fromJson<APIResponse>(reader, APIResponse::class.java).hits as ArrayList<Flower>

                        val thread: Thread = object: Thread() {
                            override fun run() {
                               // super.run()
                                resp.forEach { it ->
                                    requireActivity().runOnUiThread(object: Runnable {
                                        override fun run() {
                                            addImage(it)
                                        }
                                    })

                                }
                            }
                        }
                        thread.start()

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onError(error: ANError) {
                    // handle error
                    Log.d("responce_app", error.toString()) //Logs out 20
                }
            })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_photo, menu)
        btnAddPhoto = menu.findItem(R.id.action_add_photo)

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            btnAddPhoto!!.itemId -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (this.requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        val permissions = Array<String>(1){Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE)
                    }
                }

                pickImageFromGallery()

            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun pickImageFromGallery() {
        val intent: Intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) {
            return
        }
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
           addImage(Flower(photoUri= data.data as android.net.Uri))
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun addImage(data: Flower) {
        images.add(data)
        photoAdapter!!.setPhotos(images)

        if (images.isNotEmpty()) {
            loadingIndicator!!.hide()
        }
    }

    fun bitMapToUri(bitMap: Bitmap) : android.net.Uri {
        val bytes = ByteArrayOutputStream()

        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(
            this.requireContext().contentResolver,
            bitMap,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this.context, "Please allow to use gallery", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FourthFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FourthFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
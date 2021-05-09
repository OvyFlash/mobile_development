package ua.kpi.comsys.io8130.FirstFragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import androidx.loader.content.AsyncTaskLoader
import java.io.InputStream
import java.net.URL

//
//class LoadImage(): AsyncTask<String, Void, Bitmap> {
//    private var bmImage: ImageView? = null
//
//    fun DownloadImageTask(bmImage: ImageView?) {
//        this.bmImage = bmImage
//    }
//
//    protected fun doInBackground(vararg urls: String?): Bitmap? {
//        val urldisplay = urls[0]
//        var mIcon11: Bitmap? = null
//        try {
//            val `in`: InputStream = URL().openStream()
//            mIcon11 = BitmapFactory.decodeStream(`in`)
//        } catch (e: Exception) {
//            Log.e("Error", e.message)
//            e.printStackTrace()
//        }
//        return mIcon11
//    }
//
//    protected fun onPostExecute(result: Bitmap?) {
//        bmImage.setImageBitmap(result)
//    }
//}
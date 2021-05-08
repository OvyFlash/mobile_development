package ua.kpi.comsys.io8130.FourthFragment

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.provider.Contacts
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import ua.kpi.comsys.io8130.R
import ua.kpi.comsys.io8130.ThirdFragment.Models.Movie
import java.io.File

public class PhotoAdapter(context: Context, photos: ArrayList<Uri>) : RecyclerView.Adapter<PhotoAdapter.MyViewHolder>() {
    var context: Context? = context
    var photos: ArrayList<android.net.Uri>? = photos

    override fun getItemCount(): Int {
        if (photos == null) {
            return 0
        }

        return photos!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.photoHolder!!.setImageURI(photos!![position])
        val item: android.net.Uri = this.photos!![position]
//        testPhoto!!.setImageURI(item)
//        Log.e("131231243", item.path!!)
//        holder.photoHolder!!.setImageURI(item)
//        Glide.with(holder.itemView.context)
//            .load(File(item.path!!))
//            .transform(CenterCrop())
//            .into(holder.photoHolder!!)
//        if (photos!!.size % 7 == 0 || photos!!.size % 12 == 0) {
//            holder.photoHolder!!.maxHeight = 200
//        } else {
//            holder.photoHolder!!.maxHeight = 100
//        }
        //holder.photoHolder!!.setImageURI(item)
        Glide.with(holder.itemView.context).load(item).transforms(CenterCrop()).into(holder.photoHolder!!)
    }

    @JvmName("setPhotos1")
    public final fun setPhotos(photos: ArrayList<android.net.Uri>) {
        this.photos = ArrayList<android.net.Uri>(photos)
        notifyDataSetChanged()
        Log.e("13", "UPDATED PHOTO ADDED NEW")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.photo_item, parent, false)

        return MyViewHolder(view)
    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var photoHolder: ImageView? = null
        init {
            super.itemView
            photoHolder = itemView.findViewById(R.id.my_im1)
//            photosHolder[1] = itemView.findViewById(R.id.my_im2)
//            photosHolder[2] = itemView.findViewById(R.id.my_im3)
//            photosHolder[3] = itemView.findViewById(R.id.my_im4)
//            photosHolder[4] = itemView.findViewById(R.id.my_im5)
//            photosHolder[5] = itemView.findViewById(R.id.my_im6)
//            photosHolder[6] = itemView.findViewById(R.id.my_im7)
//            photosHolder[7] = itemView.findViewById(R.id.my_im8)
//            photosHolder[8] = itemView.findViewById(R.id.my_im9)

//            val photo1: ImageView = itemView.findViewById(R.id.my_im1)
//            val photo2: ImageView = itemView.findViewById(R.id.my_im2)
//            val photo3: ImageView = itemView.findViewById(R.id.my_im3)
//            val photo4: ImageView = itemView.findViewById(R.id.my_im4)
//            val photo5: ImageView = itemView.findViewById(R.id.my_im5)
//            val photo6: ImageView = itemView.findViewById(R.id.my_im6)
//            val photo7: ImageView = itemView.findViewById(R.id.my_im7)
//            val photo8: ImageView = itemView.findViewById(R.id.my_im8)
//            val photo9: ImageView = itemView.findViewById(R.id.my_im9)
        }
    }
}
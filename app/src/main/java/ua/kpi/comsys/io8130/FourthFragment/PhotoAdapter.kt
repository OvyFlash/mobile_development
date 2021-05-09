package ua.kpi.comsys.io8130.FourthFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import ua.kpi.comsys.io8130.FourthFragment.Models.Flower
import ua.kpi.comsys.io8130.LoadingIndicator
import ua.kpi.comsys.io8130.R


public class PhotoAdapter(context: Context, photos: ArrayList<Flower>) : RecyclerView.Adapter<PhotoAdapter.MyViewHolder>() {
    var context: Context? = context
    var photos: ArrayList<Flower>? = photos

    override fun getItemCount(): Int {
        if (photos == null) {
            return 0
        }

        return photos!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: Flower = this.photos!![position]
        holder.loading!!.setVisible()
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher_round)

        if (item.ByURI()) {
            Glide.with(holder.itemView.context)
                .load(item.largeImageURL)
                .transforms(CenterCrop())
                .into(holder.photoHolder!!)
        } else {
            Glide.with(holder.itemView.context)
                .load(item.photoUri)
                .transforms(CenterCrop())
                .apply(options)
                .into(holder.photoHolder!!)
        }
        holder.loading!!.hide()
    }

    @JvmName("setPhotos1")
    public final fun setPhotos(photos: ArrayList<Flower>) {
        this.photos = ArrayList<Flower>(photos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.photo_item, parent, false)

        return MyViewHolder(view)
    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var photoHolder: ImageView? = null
        var loading: LoadingIndicator? = null
        init {
            super.itemView
            photoHolder = itemView.findViewById(R.id.my_im1)
            loading = LoadingIndicator(itemView.findViewById<ProgressBar>(R.id.my_loading1))
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
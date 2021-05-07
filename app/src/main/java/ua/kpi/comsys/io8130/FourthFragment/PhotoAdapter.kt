package ua.kpi.comsys.io8130.FourthFragment

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ua.kpi.comsys.io8130.R

public class PhotoAdapter(requireContext: Context) : RecyclerView.Adapter<PhotoAdapter.MyViewHolder>() {
    var context: Context? = null
    var photos: Array<Int>? = null
    public fun PhotoAdapter(ctx: Context) {
        context = ctx
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        if (photos!!.size > position) {
//
//            holder.photo1?.setImageResource(photos!![position])
//        }
    }

    public fun addPhoto(photo: Int) {
//        val newPhotos = photos!! as ArrayList<Int>
//        newPhotos.add(photo)
//        this.photos = newPhotos as Array<Int>
//        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.photo_item, parent, false)

        return MyViewHolder(view)
    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val photo1: ImageView? = null
//        val photo2: ImageView? = null
//        val photo3: ImageView? = null
//        val photo5: ImageView? = null
//        val photo4: ImageView? = null
//        val photo6: ImageView? = null
//        val photo7: ImageView? = null
//        val photo8: ImageView? = null
//        val photo9: ImageView? = null
        init {
            super.itemView
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
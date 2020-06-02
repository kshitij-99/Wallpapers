package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.wallpaper_item.view.*
import kotlin.random.Random

class ImagesAdapter(private val context : Context, private val images : MutableList<String>?=null ) : RecyclerView.Adapter<ImageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.wallpaper_item,parent,false))
    }

    override fun getItemCount(): Int {
        return images!!.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images!!.get(position),context)
    }
    fun addimages(imagess: List<String>){
        val initPosition = images!!.size
        images.addAll(imagess)
        notifyItemRangeInserted(initPosition, images.size)
    }
}
class ImageViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

   val wallpaper = itemView.wallpaper as ImageView
    fun bind(image : String,context:Context){
        Glide.with(context)  //2
            .load(image) //3
            .placeholder(R.drawable.ic_launcher_background) //5
            .error(R.drawable.ic_launcher_foreground) //6
            .into(wallpaper)
        wallpaper.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, Wallaper::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("image", image)
            context.startActivity(intent)
        })
    }
}
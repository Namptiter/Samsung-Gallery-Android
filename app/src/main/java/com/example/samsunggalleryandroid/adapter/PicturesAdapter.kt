package com.example.samsunggalleryandroid.adapter
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samsunggalleryandroid.R
import com.example.samsunggalleryandroid.data.PicturesFragmentDataHeader
import com.example.samsunggalleryandroid.data.PicturesFragmentDataImg
import com.bumptech.glide.Glide
import com.example.samsunggalleryandroid.PassPositionImage
import java.lang.Math.floor


class PicturesHeaderAdapter(private val context: Context,
    private val dataset:List<PicturesFragmentDataHeader>, internal var itemClick: PassPositionImage): RecyclerView.Adapter<PicturesHeaderAdapter.ItemViewHolder>() {

    private val viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val dateText: TextView = view.findViewById(R.id.date_block_pic)
        val locationText: TextView = view.findViewById(R.id.location_block_pic)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_pictures_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.block_pictures_header,parent,false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.dateText.text = item.date
        holder.locationText.text = item.location

        val layoutManager = GridLayoutManager(holder.recyclerView.context,4)
        layoutManager.setInitialPrefetchItemCount(item.img.size)
        val imgAdapter = PicturesImgAdapter(context,item.img,itemClick)

        holder.recyclerView.setLayoutManager(layoutManager)
        holder.recyclerView.setAdapter(imgAdapter)
        holder.recyclerView.setRecycledViewPool(viewPool)
        holder.recyclerView.setHasFixedSize(true)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    override fun getItemCount() = dataset.size
}
class PicturesImgAdapter(private val context: Context,
                         private val dataset: List<PicturesFragmentDataImg>, internal var itemClick: PassPositionImage): RecyclerView.Adapter<PicturesImgAdapter.ItemViewHolder>(){


    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val ImgView: ImageView = view.findViewById(R.id.imgPath)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.block_pictures_img,parent,false)
        return ItemViewHolder(adapterLayout)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        var width: Int = context.applicationContext.resources.displayMetrics.widthPixels
        width = floor((width/4).toDouble()).toInt()
        Glide.with(context)
            .load(item.imgPath)
            .override(width,width)
            .into(holder.ImgView)
        if(item.type=="VIDEO"){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.ImgView.foreground = context.getDrawable(R.drawable.play)
            }
        }
        holder.ImgView.setOnClickListener {
            if(dataset[position].type=="IMG") itemClick.clickImage(dataset[position].count)
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    override fun getItemCount() = dataset.size
}
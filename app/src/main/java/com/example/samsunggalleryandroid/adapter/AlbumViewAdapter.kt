package com.example.samsunggalleryandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.samsunggalleryandroid.R
import com.example.samsunggalleryandroid.data.AlbumViewData

class AlbumViewAdapter(private val activity: FragmentActivity, private val context: Context,
                       private val dataset: MutableList<AlbumViewData>
): RecyclerView.Adapter<AlbumViewAdapter.ItemViewHolder>() {

    val AlbumId:Int = 0

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val img = view.findViewById<ImageView>(R.id.img_album_view)
        val nameImg = view.findViewById<TextView>(R.id.txt_album_view)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int, ): AlbumViewAdapter.ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.block_album_view,parent,false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.img.setImageURI(item.path)
        holder.nameImg.setText(item.name)
    }

    override fun getItemCount() = dataset.size

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}
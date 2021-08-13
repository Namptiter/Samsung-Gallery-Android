package com.example.samsunggalleryandroid.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.samsunggalleryandroid.AlbumFragment
import com.example.samsunggalleryandroid.AlbumviewFragment
import com.example.samsunggalleryandroid.ImageInAlbumFragment
import com.example.samsunggalleryandroid.R
import com.example.samsunggalleryandroid.data.AlbumData
import com.example.samsunggalleryandroid.data.AlbumImage
import com.example.samsunggalleryandroid.database.AppDataBase

class AlbumImageInAdapter(private val activity: FragmentActivity, private val context: Context,
                   private val dataset: List<AlbumImage>
): RecyclerView.Adapter<AlbumImageInAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val img = view.findViewById<ImageView>(R.id.img_in_album_view)
        val txt = view.findViewById<TextView>(R.id.txt_in_album_view)
        val del = view.findViewById<ImageButton>(R.id.delete_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.block_img_in_album,parent,false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.img.setImageURI(item.imageUri.toUri())
        holder.txt.setText(item.imgName)

        holder.del.setOnClickListener {
            val db_Info = Room.databaseBuilder(
                context,
                AppDataBase::class.java, "AlbumData1"
            ).allowMainThreadQueries().build()
            val a = db_Info.imageDao()
            a.deleteImgByName(item.imgName)
            db_Info.close()
            holder.img.visibility = View.GONE
            holder.txt.visibility = View.GONE
            holder.del.visibility = View.GONE
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    override fun getItemCount(): Int  = dataset.size

}
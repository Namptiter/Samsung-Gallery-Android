package com.example.samsunggalleryandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.samsunggalleryandroid.AlbumFragment
import com.example.samsunggalleryandroid.AlbumviewFragment
import com.example.samsunggalleryandroid.ImageInAlbumFragment
import com.example.samsunggalleryandroid.R
import com.example.samsunggalleryandroid.data.AlbumData
import com.example.samsunggalleryandroid.database.AppDataBase

class AlbumAdapter(private val activity: FragmentActivity, private val context: Context,
                   private val dataset: List<AlbumData>
): RecyclerView.Adapter<AlbumAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val albumname = view.findViewById<TextView>(R.id.txt_album_name)
        val bntDelete = view.findViewById<ImageButton>(R.id.bnt_delete_album)
        val addImage = view.findViewById<ImageButton>(R.id.bnt_add_img_to_album)
        val viewAlbum = view.findViewById<ImageView>(R.id.img_album)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.block_album_name,parent,false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.albumname.text = item.albumName

        //Delete Album
        holder.bntDelete.setOnClickListener {
            val albumName = dataset[position].albumName
            val db_Info = Room.databaseBuilder(
                context,
                AppDataBase::class.java, "AlbumData1"
            ).allowMainThreadQueries().build()
            val albumDao = db_Info.albumDao()
            val albumID = albumDao.getByAlbumName(albumName)
            albumDao.deleteByName(albumName)

            val b = db_Info.imageDao()
            b.deleteAllImageInAlbum(albumID[0].id)
            db_Info.close()

            val fr = AlbumFragment()
            activity.supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,fr)
                commit()
            }
        }

        //Add image to album
        holder.addImage.setOnClickListener {
            val fr = AlbumviewFragment()
            fr.albumName = item.albumName
            activity.supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,fr)
                commit()
            }
        }

        //View album
        holder.viewAlbum.setOnClickListener {
            val db_Info = Room.databaseBuilder(
                context,
                AppDataBase::class.java, "AlbumData1"
            ).allowMainThreadQueries().build()

            val a = db_Info.albumDao()
            val b = db_Info.imageDao()

            val albumID = a.getByAlbumName(item.albumName)[0].id
            val listImg = b.getImagebyAlbumID(albumID)
            db_Info.close()
            val fr = ImageInAlbumFragment()
            fr.albumID = albumID
            fr.data = listImg
            fr.albumName = item.albumName
            activity.supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,fr)
                commit()
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    override fun getItemCount(): Int  = dataset.size
}
package com.example.samsunggalleryandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.samsunggalleryandroid.adapter.AlbumImageInAdapter
import com.example.samsunggalleryandroid.data.AlbumImage
import com.example.samsunggalleryandroid.database.AppDataBase

class ImageInAlbumFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    var albumID: Int =  0
    var albumName: String = ""
    lateinit var data: List<AlbumImage>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_image_in_album, container, false)
        view.findViewById<TextView>(R.id.txt_album_in_name).setText(albumName)

        initData(view)

        recyclerView = view.findViewById(R.id.recycler_img_in_album)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(view.getContext()))
        recyclerView.setAdapter(activity?.let { AlbumImageInAdapter(it,view.getContext(),data) })

        view.findViewById<Button>(R.id.back).setOnClickListener {
            val fr = AlbumFragment()
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.flFragment,fr)
                commit()
            }
        }
        return view
    }

    fun initData(view:View){
        val db_Image = Room.databaseBuilder(
            view.context,
            AppDataBase::class.java, "AlbumData1"
        ).allowMainThreadQueries().build()

        val a = db_Image.imageDao()
        db_Image.close()

        data = a.getImagebyAlbumID(albumID)
    }
}
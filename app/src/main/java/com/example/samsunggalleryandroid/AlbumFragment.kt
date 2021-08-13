package com.example.samsunggalleryandroid

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.samsunggalleryandroid.adapter.AlbumAdapter
import com.example.samsunggalleryandroid.data.AlbumData
import com.example.samsunggalleryandroid.database.AppDataBase

class AlbumFragment : Fragment() {

        private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    @SuppressLint("ShowToast")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        val view:View =  inflater.inflate(R.layout.fragment_album, container, false)
        //Album Database build
        val db_Info = Room.databaseBuilder(
            view.context,
            AppDataBase::class.java, "AlbumData1"
        ).allowMainThreadQueries().build()

        val albumInfoDao = db_Info.albumDao()
        val albumInfo:List<AlbumData> = albumInfoDao.getAll()
        db_Info.close()

        recyclerView = view.findViewById(R.id.albumRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(view.getContext()))
        recyclerView.setAdapter(activity?.let { AlbumAdapter(it,view.getContext(),albumInfo) })

        //Create new album
        view.findViewById<Button>(R.id.bnt_create_album).setOnClickListener {
            val AlBumName = view.findViewById<TextView>(R.id.input_album_name).text.toString()
            val db_Info = Room.databaseBuilder(
                view.context,
                AppDataBase::class.java, "AlbumData1"
            ).allowMainThreadQueries().build()

            val albumInfoDao = db_Info.albumDao()
            val getAlbumName = albumInfoDao.getByAlbumName(AlBumName)
            if(getAlbumName.size==0){
                val newAlbum = AlbumData(0,AlBumName)
                albumInfoDao.insertAlbum(newAlbum)
                val fr = AlbumFragment()
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.flFragment,fr)
                    commit()
                }
            }else Toast.makeText(activity,"This Album is exist",Toast.LENGTH_SHORT)
            db_Info.close()
        }
        return view
    }
}
package com.example.samsunggalleryandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.samsunggalleryandroid.adapter.AlbumViewAdapter
import com.example.samsunggalleryandroid.data.AlbumImage
import com.example.samsunggalleryandroid.data.AlbumViewData
import com.example.samsunggalleryandroid.database.AppDataBase
import com.example.samsunggalleryandroid.model.FileViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AlbumviewFragment : Fragment() {

    var albumName: String = ""
    private var listView = mutableListOf<AlbumViewData>()
    private lateinit var viewModel: FileViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        val view =  inflater.inflate(R.layout.fragment_albumview, container, false)

        view.findViewById<TextView>(R.id.txt_album).setText(albumName)

        initData()

        recyclerView = view.findViewById(R.id.albumViewRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(view.getContext()))
        recyclerView.setAdapter(activity?.let { AlbumViewAdapter(it,view.getContext(),listView) })

        //All all Image checked to Album
        view.findViewById<Button>(R.id.bnt_create_album_view).setOnClickListener {
            val db_Info = Room.databaseBuilder(
                view.context,
                AppDataBase::class.java, "AlbumData1"
            ).allowMainThreadQueries().build()
            val a = db_Info.albumDao()
            val getAbumByName =  a.getByAlbumName(albumName)
            val AlbumId = getAbumByName[0].id

            val b = db_Info.imageDao()

            for(i in (0..listView.size)){
                if(recyclerView.layoutManager?.findViewByPosition(i)?.findViewById<CheckBox>(R.id.check_album_view)?.isChecked == true){
                    // Add to data base
                    b.insertAlbum(AlbumImage(0,AlbumId,listView.get(i).name,listView.get(i).path.toString()))
                }
            }
            db_Info.close()
            val fr = AlbumFragment()
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.flFragment,fr)
                commit()
            }
        }
        return view
    }
    private fun initData(){
        if(listView.size!=0) return
        viewModel = activity?.let { ViewModelProvider(it).get(FileViewModel::class.java) }!!
        viewModel.imageData.observe(viewLifecycleOwner,{
            GlobalScope.launch {
                for(i in it){
                    listView.add(AlbumViewData(i.imgPath,i.name))
                }
            }
        })
    }
}
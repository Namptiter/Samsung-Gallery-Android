package com.example.samsunggalleryandroid

import android.os.Bundle
import android.view.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samsunggalleryandroid.adapter.PicturesHeaderAdapter
import com.example.samsunggalleryandroid.data.PicturesFragmentDataHeader
import com.example.samsunggalleryandroid.data.PicturesFragmentDataImg
import com.example.samsunggalleryandroid.model.FileViewModel


class PicturesFragment() : Fragment() {
    lateinit var tmp: List<PicturesFragmentDataImg>
    var Header = mutableListOf<PicturesFragmentDataHeader>()
    private lateinit var recyclerView: RecyclerView

    private val viewModel: FileViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pictures, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_pictures)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(view.getContext()))
        recyclerView.setAdapter(PicturesHeaderAdapter(view.getContext(),Header))
        return view
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        initialData()
        super.onViewCreated(itemView, savedInstanceState)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    private fun initialData(){
        if(Header.size!=0) return
        viewModel.imageData.observe(viewLifecycleOwner, Observer<List<PicturesFragmentDataImg>>{
            var groupByDate = it.groupBy{it.date}
            for(group in groupByDate){
                var listImg = mutableListOf<PicturesFragmentDataImg>()
                for(imgData in group.value){
                    listImg.add(imgData)
                }
                Header.add(PicturesFragmentDataHeader(group.key,"Ha Noi",listImg))
            }
        })
    }
}
package com.example.samsunggalleryandroid

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samsunggalleryandroid.adapter.PicturesHeaderAdapter
import com.example.samsunggalleryandroid.data.PicturesFragmentDataHeader
import com.example.samsunggalleryandroid.data.PicturesFragmentDataImg
import com.example.samsunggalleryandroid.model.FileViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PicturesFragment() : Fragment(),PassPositionImage {
    var Header = mutableListOf<PicturesFragmentDataHeader>()

    private lateinit var recyclerView: RecyclerView

    private lateinit var viewModel: FileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pictures, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_pictures)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(view.getContext()))
        recyclerView.setAdapter(PicturesHeaderAdapter(view.getContext(),Header,this))
        return view
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        initialData()
        super.onViewCreated(itemView, savedInstanceState)
    }
    private fun initialData(){
        if(Header.size!=0) return
        viewModel = activity?.let { ViewModelProvider(it).get(FileViewModel::class.java) }!!
        viewModel.imageData.observe(viewLifecycleOwner, Observer<List<PicturesFragmentDataImg>>{
            val groupByDate = it.groupBy{it.date}
            for(group in groupByDate){
                GlobalScope.async{
                    val listImg = mutableListOf<PicturesFragmentDataImg>()
                    for(imgData in group.value){
                        GlobalScope.async { listImg.add(imgData) }
                    }
                    Header.add(PicturesFragmentDataHeader(group.key,"Ha Noi",listImg))
                }
            }
        })
    }

    override fun clickImage(position: Int) {
        viewModel = activity?.let { ViewModelProvider(it).get(FileViewModel::class.java) }!!
        viewModel.position = MutableLiveData(position)
        val imageFragment = ImageFragment()
        setCurrentFragment(imageFragment)
    }
    fun setCurrentFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
    }
}
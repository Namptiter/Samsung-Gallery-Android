package com.example.samsunggalleryandroid

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samsunggalleryandroid.adapter.PicturesHeaderAdapter
import com.example.samsunggalleryandroid.data.PicturesFragmentDataHeader
import com.example.samsunggalleryandroid.data.PicturesFragmentDataImg
import com.example.samsunggalleryandroid.model.FileViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.*

class PicturesFragment() : Fragment(),PassPositionImage {

    var Header = mutableListOf<PicturesFragmentDataHeader>()
    private lateinit var recyclerView: RecyclerView
    lateinit var viewModel: FileViewModel
    var modeSelect: String = "WEEK"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pictures, container, false)

        onSwith(view)
        initialData()

        Header = mutableListOf<PicturesFragmentDataHeader>()
        recyclerView = view.findViewById(R.id.recycler_view_pictures)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(view.getContext()))
        recyclerView.setAdapter(PicturesHeaderAdapter(view.getContext(),Header,this))
        if(modeSelect=="WEEK") view.findViewById<Button>(R.id.b_week).setBackgroundColor(Color.parseColor("#8BC34A"))
        else if(modeSelect=="MONTH") view.findViewById<Button>(R.id.btn_month).setBackgroundColor(Color.parseColor("#8BC34A"))
        else if(modeSelect=="YEAR") view.findViewById<Button>(R.id.bnt_year).setBackgroundColor(Color.parseColor("#8BC34A"))
        return view
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {

        super.onViewCreated(itemView, savedInstanceState)
    }
    private fun initialData(){
        viewModel = activity?.let { ViewModelProvider(it).get(FileViewModel::class.java) }!!
        viewModel.imageData.observe(viewLifecycleOwner, {
            var groupByDate: Map<String, List<PicturesFragmentDataImg>> = it.groupBy{ it.Week }
            if(modeSelect=="WEEK") groupByDate = it.groupBy { it.Week }
            else if(modeSelect=="MONTH") groupByDate = it.groupBy { it.Month }
            else if(modeSelect=="YEAR") groupByDate = it.groupBy { it.Year }
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
    private fun setCurrentFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
    }
    private fun reCall(fragment: Fragment){
        activity?.supportFragmentManager?.beginTransaction()?.detach(fragment)?.attach(fragment)?.commit()
    }
    private fun onSwith(view: View){
        view.findViewById<Button>(R.id.b_week).setOnClickListener {
            if(modeSelect!="WEEK"){
                initColorButton(view)
                val t = PicturesFragment()
                t.modeSelect = "WEEK"
                setCurrentFragment(t)
            }
        }
        view.findViewById<Button>(R.id.btn_month).setOnClickListener {
            if(modeSelect!="MONTH"){
                initColorButton(view)
                val t = PicturesFragment()
                t.modeSelect = "MONTH"
                setCurrentFragment(t)
            }
        }
        view.findViewById<Button>(R.id.bnt_year).setOnClickListener {
            if(modeSelect!="YEAR"){
                initColorButton(view)
                val t = PicturesFragment()
                t.modeSelect = "YEAR"
                setCurrentFragment(t)
            }
        }
    }

    fun initColorButton(view:View){
        view.findViewById<Button>(R.id.b_week).setBackgroundColor(Color.parseColor("#EAEAEA"))
        view.findViewById<Button>(R.id.btn_month).setBackgroundColor(Color.parseColor("#EAEAEA"))
        view.findViewById<Button>(R.id.bnt_year).setBackgroundColor(Color.parseColor("#EAEAEA"))
    }
}
package com.example.samsunggalleryandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.example.samsunggalleryandroid.data.PicturesFragmentDataImg
import com.example.samsunggalleryandroid.model.FileViewModel
import com.example.samsunggalleryandroid.service.OnSwipeTouchListener


class ImageInfoFragment : Fragment() {

    private lateinit var viewModel: FileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        val view =  inflater.inflate(R.layout.fragment_image_info, container, false)
        var position: Int = 0
        var imageInfo:PicturesFragmentDataImg = PicturesFragmentDataImg("".toUri(),0,"","","","","",0,"","","")
        viewModel = activity?.let { ViewModelProvider(it).get(FileViewModel::class.java) }!!
        activity?.let {
            viewModel.position.observe(it,{
                position = it
            })
        }
        activity?.let {
            viewModel.imageData.observe(it, {
               imageInfo =  it[position]
            })
        }
        view.findViewById<ImageView>(R.id.imageView2).setImageURI(imageInfo.imgPath)
        view.findViewById<TextView>(R.id.txt_img_name).setText(imageInfo.name)
        view.findViewById<TextView>(R.id.txt_img_date).setText(imageInfo.fullDate)
        view.findViewById<TextView>(R.id.txt_img_size).setText(imageInfo.size.toString())

        val onSwipe = view.findViewById<ImageView>(R.id.imageView2)
        onSwipe.setOnTouchListener(object: OnSwipeTouchListener(activity){
            override fun onSwipeDown() {
                super.onSwipeDown()
                val p = PicturesFragment()
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.flFragment,p)
                    commit()
                }
            }
        })
        return view
    }
}
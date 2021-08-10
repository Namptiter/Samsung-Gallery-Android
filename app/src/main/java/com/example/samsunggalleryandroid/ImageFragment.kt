package com.example.samsunggalleryandroid

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.samsunggalleryandroid.model.FileViewModel
import com.example.samsunggalleryandroid.service.OnSwipeTouchListener

class ImageFragment : Fragment() {

    private lateinit var viewModel: FileViewModel
    private var position:Int = 0
    val picturesFragment = PicturesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        val view =  inflater.inflate(R.layout.fragment_image, container, false)

        var imgUri: Uri = "//".toUri()

        viewModel = activity?.let { ViewModelProvider(it).get(FileViewModel::class.java) }!!
        activity?.let {
            viewModel.position.observe(it,{
                position = it;
            })
        }
        activity?.let {
            viewModel.imageData.observe(it,{
                imgUri = it[position].imgPath
            })
        }

        val onSwipe = view.findViewById<ImageView>(R.id.imageShow)
        onSwipe.setOnTouchListener(object: OnSwipeTouchListener(activity){
            override fun onSwipeLeft() {
                var MAX = 0
                super.onSwipeLeft()
                activity?.let {
                    viewModel.position.observe(it,{
                        position = it;
                    })
                }
                activity?.let {
                    viewModel.max.observe(it,{
                        MAX = it;
                    })
                }
                if(position+1>=MAX) return
                activity?.let {
                    viewModel.position.observe(it,{
                        viewModel.position = MutableLiveData(it + 1)
                    })
                }
                activity?.let {
                    viewModel.position.observe(it,{
                        position = it;
                    })
                }
                activity?.let {
                    viewModel.imageData.observe(it,{
                        imgUri = it[position].imgPath
                    })
                }
                view.findViewById<ImageView>(R.id.imageShow).setImageURI(imgUri)
            }
            override fun onSwipeRight() {
                super.onSwipeRight()
                activity?.let {
                    viewModel.position.observe(it,{
                        position = it;
                    })
                }
                if(position-1<0) return
                activity?.let {
                    viewModel.position.observe(it,{
                        viewModel.position = MutableLiveData(it-1)
                    })
                };
                activity?.let {
                    viewModel.position.observe(it,{
                        position = it;
                    })
                }
                activity?.let {
                    viewModel.imageData.observe(it,{
                        imgUri = it[position].imgPath
                    })
                }
                view.findViewById<ImageView>(R.id.imageShow).setImageURI(imgUri)
            }

            override fun onSwipeDown() {
                super.onSwipeDown()
                setCurrentFragment(picturesFragment)
            }
        })
        view.findViewById<ImageView>(R.id.imageShow).setImageURI(imgUri)
        return view
    }
    fun setCurrentFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
    }
}
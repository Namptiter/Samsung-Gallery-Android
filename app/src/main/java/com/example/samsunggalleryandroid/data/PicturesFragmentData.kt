package com.example.samsunggalleryandroid.data
import android.net.Uri

data class PicturesFragmentDataHeader (val date: String, val location: String, val img: List<PicturesFragmentDataImg>)
data class PicturesFragmentDataImg (val imgPath: Uri, val size: Long, val date: String, val location: String, val type: String, val fullDate:String, val name: String, val count: Int)
package com.example.samsunggalleryandroid.data
import android.net.Uri

data class PicturesFragmentDataHeader (val date: String, val location: String, val img: List<PicturesFragmentDataImg>)
data class PicturesFragmentDataImg (val imgPath: Uri, val size: Int, val date: String, val location: String)
package com.example.samsunggalleryandroid.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.samsunggalleryandroid.data.PicturesFragmentDataImg

class FileViewModel:ViewModel() {
    var imageData  = MutableLiveData<List<PicturesFragmentDataImg>>()
    var position = MutableLiveData<Int>()
    var max = MutableLiveData<Int>()
}
package com.example.samsunggalleryandroid.service

import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.example.samsunggalleryandroid.MainActivity
import com.example.samsunggalleryandroid.data.PicturesFragmentDataImg
import java.util.*

class FileHandle {
    fun readMediaData(activity: MainActivity):MutableList<PicturesFragmentDataImg> {
        var count = 0
        val imgPath = mutableListOf<PicturesFragmentDataImg>()
        var uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        var imageProjection = arrayOf(
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATE_MODIFIED,
            MediaStore.Images.Media._ID,
        )

        val cursor = activity.getContentResolver().query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            imageProjection,
            null,
            null,
            null)

        if (cursor != null) {
            val columnIndexID  = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val timeID = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)
            val sizeID = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
            val nameID = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            while (cursor.moveToNext()) {
                val imageId:Long = cursor.getLong(columnIndexID)
                val uriImage = Uri.withAppendedPath(uriExternal, "" + imageId)
                val allDate = Date(cursor.getLong(timeID)*1000).toString()
                val date:String = allDate.substring(8,10) + ", " + allDate.substring(4,7)
                val Week: String = allDate.substring(0,3) + ", " + allDate.substring(4,7)+", "+allDate.substring(30,34)
                val Month: String = allDate.substring(4,7)+", "+allDate.substring(30,34)
                val Year: String = allDate.substring(30,34)
                val size: Long = cursor.getLong(sizeID)
                val name: String = cursor.getString(nameID)
                imgPath.add(PicturesFragmentDataImg(uriImage,size,date,"Ha Noi","IMG",allDate,name,count++,Week,Month,Year))
            }
            cursor.close()
        }

        //Read video
//        uriExternal = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
//        imageProjection = arrayOf(
//            MediaStore.Video.Media.DISPLAY_NAME,
//            MediaStore.Video.Media.SIZE,
//            MediaStore.Video.Media.DATE_MODIFIED,
//            MediaStore.Video.Media._ID
//        )
//        val videoCur = activity.getContentResolver().query(
//            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//            imageProjection,
//            null,
//            null,
//            null)
//        if(videoCur!=null){
//            val colVideoName = videoCur.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
//            val colVideoSize = videoCur.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
//            val colVideoDate = videoCur.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED)
//            val colVideoId = videoCur.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
//            while (videoCur.moveToNext()){
//                val allDate = Date(videoCur.getLong(colVideoDate)*1000).toString()
//                val Week: String = allDate.substring(0,3) + ", " + allDate.substring(4,7)+", "+allDate.substring(30,34)
//                val Month: String = allDate.substring(4,7)+", "+allDate.substring(30,34)
//                val Year: String = allDate.substring(30,34)
//                val videoName = videoCur.getString(colVideoName)
//                val videoSize = videoCur.getLong(colVideoSize)
//                val videoDate = allDate.substring(4,7)+", "+allDate.substring(8,10)
//                val videoId = videoCur.getLong(colVideoId)
//                val videoUri = Uri.withAppendedPath(uriExternal, "" + videoId)
//                imgPath.add(PicturesFragmentDataImg(videoUri, videoSize, videoDate,"Ha Noi","VIDEO", allDate, videoName,count++,Week,Month,Year))
//            }
//            videoCur.close()
//        }
        return imgPath
    }
}
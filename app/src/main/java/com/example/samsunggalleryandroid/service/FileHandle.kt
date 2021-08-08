package com.example.samsunggalleryandroid.service

import android.net.Uri
import android.provider.MediaStore
import com.example.samsunggalleryandroid.MainActivity
import com.example.samsunggalleryandroid.data.PicturesFragmentDataImg
import java.util.*

class FileHandle {
    fun readMediaData(activity: MainActivity):MutableList<PicturesFragmentDataImg> {
        val imgPath = mutableListOf<PicturesFragmentDataImg>()
        val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val imageProjection = arrayOf(
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATE_MODIFIED,
            MediaStore.Images.Media._ID
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
            while (cursor.moveToNext()) {
                val imageId:Long = cursor.getLong(columnIndexID)
                val uriImage = Uri.withAppendedPath(uriExternal, "" + imageId)
                val date:String = Date(cursor.getLong(timeID)*1000).toString().substring(4,7) + ", " + Date(cursor.getLong(timeID)*1000).toString().substring(8,10)
                val size:Int = cursor.getLong(sizeID).toInt()
                imgPath.add(PicturesFragmentDataImg(uriImage,size,date,"Ha Noi"))
            }
            cursor.close()
        }
        return imgPath
    }
}
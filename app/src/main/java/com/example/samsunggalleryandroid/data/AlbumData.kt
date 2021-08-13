package com.example.samsunggalleryandroid.data

import android.net.Uri
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlbumData(
    @PrimaryKey(autoGenerate  = true) val id: Int,
    @NonNull @ColumnInfo(name = "album_name") val albumName: String
)
@Entity
data class AlbumImage(
    @PrimaryKey(autoGenerate  = true) val id: Int,
    @NonNull @ColumnInfo(name = "album_id") val albumID: Int,
    @NonNull @ColumnInfo(name = "img_name") val imgName: String,
    @NonNull @ColumnInfo(name = "image_uri") val imageUri: String
)
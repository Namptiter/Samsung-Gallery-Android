package com.example.samsunggalleryandroid.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.samsunggalleryandroid.DaoAlbumData
import com.example.samsunggalleryandroid.DaoAlbumImage
import com.example.samsunggalleryandroid.data.AlbumData
import com.example.samsunggalleryandroid.data.AlbumImage

@Database(entities = arrayOf(AlbumData::class, AlbumImage::class), version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun albumDao(): DaoAlbumData
    abstract fun imageDao(): DaoAlbumImage
}


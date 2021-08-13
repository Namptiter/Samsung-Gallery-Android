package com.example.samsunggalleryandroid

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.samsunggalleryandroid.data.AlbumImage

@Dao
interface DaoAlbumImage {
    @Query("SELECT * FROM AlbumImage")
    fun getAll():List<AlbumImage>

    @Query("SELECT * FROM AlbumImage WHERE album_id=:id")
    fun getImagebyAlbumID(id:Int):List<AlbumImage>

    @Insert
    fun insertAlbum(album: AlbumImage)

    @Delete
    fun deleteAlbum(album: AlbumImage)

    @Query("DELETE FROM AlbumImage WHERE album_id=:id")
    fun deleteAllImageInAlbum(id:Int)

    @Query("DELETE FROM AlbumImage WHERE img_name=:name")
    fun deleteImgByName(name:String)
}
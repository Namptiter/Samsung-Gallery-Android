package com.example.samsunggalleryandroid

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.samsunggalleryandroid.data.AlbumData

@Dao
interface DaoAlbumData {
    @Query("SELECT * FROM AlbumData")
    fun getAll():List<AlbumData>

    @Query("SELECT * FROM AlbumData WHERE album_name=:name")
    fun getByAlbumName(name: String): List<AlbumData>

    @Insert
    fun insertAlbum(album: AlbumData)

    @Delete
    fun deleteAlbum(album: AlbumData)

    @Query("DELETE FROM AlbumData WHERE album_name = :name")
    fun deleteByName(name:String)
}

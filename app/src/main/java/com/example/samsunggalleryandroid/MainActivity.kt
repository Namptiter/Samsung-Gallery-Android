package com.example.samsunggalleryandroid

import android.Manifest

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.samsunggalleryandroid.data.PicturesFragmentDataImg
import com.example.samsunggalleryandroid.databinding.ActivityMainBinding
import com.example.samsunggalleryandroid.model.FileViewModel
import com.example.samsunggalleryandroid.service.FileHandle

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val picturesFragment = PicturesFragment()
    private val albumFragment = AlbumFragment()
    private val storiesFragment = StoriesFragment()
    private val shareFragment = ShareFragment()

    private lateinit var viewModel: FileViewModel

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            101
        )

        //Read images
        val enGin = FileHandle()
        val imgUri:List<PicturesFragmentDataImg> = enGin.readMediaData(this@MainActivity)

        //Add data to model view
        viewModel = ViewModelProvider(this).get(FileViewModel::class.java)
        viewModel.imageData = MutableLiveData(imgUri)
        viewModel.max  = MutableLiveData(imgUri.size)

        //initial fragment
        setCurrentFragment(picturesFragment)

        //switch fragment
        supportActionBar?.hide()
        binding.bottomNavigationView.setOnItemSelectedListener{item->
            when(item.itemId){
                R.id.menu_nav_pic -> {
                    setCurrentFragment(picturesFragment)
                    true
                }
                R.id.menu_nav_alb -> {
                    setCurrentFragment(albumFragment)
                    true
                }
                R.id.menu_nav_sto -> {
                    setCurrentFragment(storiesFragment)
                    true
                }
                R.id.menu_nav_shr -> {
                    setCurrentFragment(shareFragment)
                    true
                }
                else -> false
            }
        }
    }
    //Set fragment
    fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
    }
}

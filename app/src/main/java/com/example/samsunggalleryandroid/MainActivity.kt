package com.example.samsunggalleryandroid

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

        //initial fragment
        setCurrentFragment(picturesFragment)

        binding.bottomNavigationView.setOnItemSelectedListener{item->
            when(item.itemId){
                R.id.menu_nav_pic -> {
                    this.supportActionBar?.show()
                    setCurrentFragment(picturesFragment)
                    true
                }
                R.id.menu_nav_alb -> {
                    this.supportActionBar?.hide()
                    setCurrentFragment(albumFragment)
                    true
                }
                R.id.menu_nav_sto -> {
                    this.supportActionBar?.hide()
                    setCurrentFragment(storiesFragment)
                    true
                }
                R.id.menu_nav_shr -> {
                    this.supportActionBar?.hide()
                    setCurrentFragment(shareFragment)
                    true
                }
                else -> false
            }
        }
    }

    //Set fragment
    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
    }
}

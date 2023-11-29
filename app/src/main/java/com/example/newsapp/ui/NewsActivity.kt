package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.viewmodel.NewsActivityViewModel
import com.example.newsapp.viewmodel.NewsActivityViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {
//    lateinit var newsViewModel: NewsActivityViewModel
//    lateinit var newsActivityViewModelFactory: NewsActivityViewModelFactory
    @Inject lateinit var newsRepository: NewsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        newsRepository.print()
//        newsActivityViewModelFactory = NewsActivityViewModelFactory(newsRepository)
//        newsViewModel = ViewModelProvider(this, newsActivityViewModelFactory).get(NewsActivityViewModel::class.java)
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setupWithNavController(
            findViewById<View>(R.id.nav_host_fragment).findNavController()
        )
    }
}
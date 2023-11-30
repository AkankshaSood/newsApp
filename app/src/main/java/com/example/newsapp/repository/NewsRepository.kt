package com.example.newsapp.repository

import android.util.Log
import com.example.newsapp.api.NewsApi
import com.example.newsapp.db.ArticleDb
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class NewsRepository @Inject constructor(val articleDb: ArticleDb) {
    fun print() {
        Log.d("heyyyyyyyyy", "kkk")
    }
}
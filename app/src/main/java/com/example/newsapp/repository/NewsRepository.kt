package com.example.newsapp.repository

import android.util.Log
import com.example.newsapp.api.NewsApi
import com.example.newsapp.db.ArticleDb
import com.example.newsapp.model.ArticlesItem
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(val articleDb: ArticleDb, val newsApi: NewsApi) {
    suspend fun getBreakingNews(pageNumber: Int = 1): Response<com.example.newsapp.model.Response>
        = newsApi.getBreakingNews(pageNumber = pageNumber)

    suspend fun searchNews(searchQuery: String): Response<com.example.newsapp.model.Response>
        = newsApi.getSearchResults(searchQuery)

    suspend fun upsert(article: ArticlesItem) = articleDb.getDAO().upsert(article)

    suspend fun deleteArticle(article: ArticlesItem) = articleDb.getDAO().deleteArticles(article)

    fun getSavedNews() = articleDb.getDAO().getArticles()

}
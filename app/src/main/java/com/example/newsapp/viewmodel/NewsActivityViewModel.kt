package com.example.newsapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.ArticlesItem

import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsActivityViewModel @Inject constructor(private val newsRepository: NewsRepository): ViewModel() {
    var breakingNews: MutableLiveData<Resource<com.example.newsapp.model.Response>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: com.example.newsapp.model.Response? = null
    var searchNews: MutableLiveData<Resource<com.example.newsapp.model.Response>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: com.example.newsapp.model.Response? = null

    init {
        Log.d("heyyy", "init done")
        getBreakingNews()
    }

    fun test() {
        Log.d("hey", "yayayyayayyayayayyayayyayayayyayayayyayay")
    }

    public fun getBreakingNews() = viewModelScope.launch {
        Log.d("get breaking news", breakingNewsPage.toString())
        breakingNews.postValue(Resource.Loading("loading ...."))
        val response = newsRepository.getBreakingNews(breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(query: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading("loading ...."))
        val response = newsRepository.searchNews(query)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<com.example.newsapp.model.Response>): Resource<com.example.newsapp.model.Response> {
        if (response.isSuccessful) {
            response.body()?.let {
                breakingNewsPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = it
                } else {
                    it.articles?.let { it1 -> breakingNewsResponse?.articles?.addAll(it1) }
                }
                return Resource.Success(breakingNewsResponse ?: it)
            }
        }
        return Resource.Error(message = response.message())
    }

    private fun handleSearchNewsResponse(response: Response<com.example.newsapp.model.Response>): Resource<com.example.newsapp.model.Response> {
        if (response.isSuccessful) {
            response.body()?.let {
                searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = it
                } else {
                    it.articles?.let { it1 -> searchNewsResponse?.articles?.addAll(it1) }
                }
                return Resource.Success(searchNewsResponse ?: it)
            }
        }
        return Resource.Error(message = response.message())
    }

    fun saveArticle(article: ArticlesItem) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: ArticlesItem) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}
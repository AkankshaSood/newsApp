package com.example.newsapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsActivityViewModel @Inject constructor(val newsRepository: NewsRepository): ViewModel() {
    var breakingNews: MutableLiveData<Resource<com.example.newsapp.model.Response>> = MutableLiveData()

    init {
        Log.d("heyyy", "init done")
//        getBreakingNews()
    }

    private fun getBreakingNews() = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading("loading ...."))
        //val response = newsRepository.getBreakingNews()
        //breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<com.example.newsapp.model.Response>): Resource<com.example.newsapp.model.Response> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(message = response.message())
    }
}
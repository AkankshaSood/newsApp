package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.ui.adapter.NewsAdapter
import com.example.newsapp.viewmodel.NewsActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    val viewModel: NewsActivityViewModel by activityViewModels()
    lateinit var newsAdapter: NewsAdapter
    lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("hey", "there? 2")
        viewModel.test()

//        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
//        setupRecyclerListView()
//        viewModel.breakingNews.observe(viewLifecycleOwner) {response ->
//            when(response) {
//                is Resource.Success -> {
//                    //hideProgressBar()
//                    response.data?.let {
//                        newsAdapter.differ.submitList(it.articles)
//                    }
//                }
//                is Resource.Error -> {
////                    hideProgressBar()
//                    response.message?.let {
//                        Log.e("heyyy", "Error occured!")
//                    }
//                }
//                else -> {
////                    showProgressBar()
//
//                }
//            }
//        }
    }

    private fun setupRecyclerListView() {
        newsAdapter = NewsAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
        }
    }

}
package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.ui.adapter.NewsAdapter
import com.example.newsapp.utils.Resource
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

        recyclerView = view.findViewById(R.id.recycler_view)
        setupRecyclerListView()

        viewModel.breakingNews.observe(viewLifecycleOwner) {response ->
            when(response) {
                is Resource.Success -> {
                    isLoading = false
                    //hideProgressBar()
                    Log.d("heyyy", "response occured!")
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles?.toList())
                        val totalPages = (response.data.totalResults ?: 0) / 20 + 2
                        isLastPage = viewModel.breakingNewsPage == totalPages
                    }
                }
                is Resource.Error -> {
                    isLoading = false
//                    hideProgressBar()
                    response.message?.let {
                        Log.e("heyyy", "Error occured!")
                    }
                }
                else -> {
                    isLoading = true
//                    showProgressBar()

                }
            }
        }
        newsAdapter.setOnItemClickListener {
            Log.d("heyyyy", "article clicked")
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition > 0
            val isTotalMoreThanVisible = totalItemCount >= 20

            Log.d("debug", isLoading.toString())
            Log.d("debug", isLastPage.toString())
            Log.d("debug", isAtLastItem.toString())
            Log.d("debug", isNotAtBeginning.toString())
            Log.d("debug", isTotalMoreThanVisible.toString())
            Log.d("debug", isScrolling.toString())
            Log.d("debug", "------end-------")

            val shouldPaginate = !isLoading && !isLastPage && isAtLastItem  && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getBreakingNews()
                isScrolling = false
            }
        }
    }

    private fun setupRecyclerListView() {
        newsAdapter = NewsAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }

}
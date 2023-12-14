package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.ui.adapter.NewsAdapter
import com.example.newsapp.utils.Resource
import com.example.newsapp.viewmodel.NewsActivityViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    val viewModel: NewsActivityViewModel by activityViewModels()
    lateinit var newsAdapter: NewsAdapter
    lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var job: Job? = null
        view.findViewById<EditText>(R.id.search_et)?.addTextChangedListener {
            Log.d("heyy", "text is changing")
            job?.cancel()
            job = lifecycleScope.launch {
                delay(500)
                it?.let {
                    if (it.isNotEmpty()) {
                        viewModel.searchNews(it.toString())
                    }
                }
            }
        }

        recyclerView = view.findViewById(R.id.recycler_view)
        setupRecyclerListView()
        viewModel.searchNews.observe(viewLifecycleOwner) {response ->
            when(response) {
                is Resource.Success -> {
                    //hideProgressBar()
                    Log.d("heyyy", "response occured!")
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
//                    hideProgressBar()
                    response.message?.let {
                        Log.e("heyyy", "Error occured!")
                    }
                }
                else -> {
//                    showProgressBar()
                }
            }
        }

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }
    }

    private fun setupRecyclerListView() {
        newsAdapter = NewsAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
        }
    }

}
package com.example.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.ui.adapter.NewsAdapter
import com.example.newsapp.viewmodel.NewsActivityViewModel

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    val viewModel: NewsActivityViewModel by activityViewModels()
    lateinit var newsAdapter: NewsAdapter
    lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)
        initRecyclerView()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner) {
            newsAdapter.differ.submitList(it)
        }

    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
        }
    }

}
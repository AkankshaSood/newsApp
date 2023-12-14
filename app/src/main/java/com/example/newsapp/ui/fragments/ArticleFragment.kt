package com.example.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.viewmodel.NewsActivityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment(R.layout.fragment_article) {

    val viewModel: NewsActivityViewModel by activityViewModels()
    val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.article
        val webview = view.findViewById<WebView>(R.id.webview)
        webview.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Article Saved Successfully!!", Snackbar.LENGTH_LONG).show()
        }
    }


}
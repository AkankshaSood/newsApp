package com.example.newsapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.ArticlesItem

class NewsAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.article_layout, parent, false)
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(findViewById(R.id.news_iv))
            this.findViewById<TextView>(R.id.news_tv).text = article.title
            this.setOnClickListener {
                Log.d("heyyyy", "here")
                onClickListener?.let {
                    it(article)
                }
            }
        }
    }

    private var differCallback = object : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem) = oldItem.url == newItem.url
        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem) = oldItem == newItem
    }

    val differ = AsyncListDiffer<ArticlesItem>(this, differCallback)

    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    private var onClickListener: ((ArticlesItem) -> Unit)? = null

    fun setOnItemClickListener(listener : (ArticlesItem) -> Unit) {
        onClickListener = listener
    }
}
package com.example.newsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Response(
	val totalResults: Int? = null,
	val articles: List<ArticlesItem?>? = null,
	val status: String? = null
)

data class Source(
	val name: String? = null,
	val id: String? = null
)

@Entity(tableName = "article_table")
data class ArticlesItem(
	@PrimaryKey(autoGenerate = true) val id: Long?,
	val publishedAt: String? = null,
	val author: String? = null,
	val urlToImage: String? = null,
	val description: String? = null,
	val title: String? = null,
	val url: String? = null,
	val content: String? = null
)


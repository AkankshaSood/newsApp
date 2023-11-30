package com.example.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.model.ArticlesItem

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(articlesItem: ArticlesItem): Long

    // live data does not work with suspend objects
    @Query("SELECT * FROM article_table")
    fun getArticles(): LiveData<List<ArticlesItem>>

    @Delete
    suspend fun deleteArticles(articlesItem: ArticlesItem)
}
package com.example.newsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.model.ArticlesItem

@Database(entities = [ArticlesItem::class], version = 1)
abstract class ArticleDb: RoomDatabase() {
    abstract fun getDAO(): ArticleDao
}
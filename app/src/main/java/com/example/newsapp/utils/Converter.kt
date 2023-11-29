package com.example.newsapp.utils

import androidx.room.TypeConverter
import com.example.newsapp.model.Source

class Converter {
    @TypeConverter
    fun fromSource(source: Source) = source.name

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}
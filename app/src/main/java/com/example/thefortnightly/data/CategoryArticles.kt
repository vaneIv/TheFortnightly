package com.example.thefortnightly.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_articles")
data class CategoryArticles(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val articleUrl: String,
    val articleCategory: String
)

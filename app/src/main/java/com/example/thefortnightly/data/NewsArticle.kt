package com.example.thefortnightly.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_articles")
data class NewsArticle(
    val title: String,
    @PrimaryKey val url: String,
    val content: String,
    val description: String,
    val category: String,
    val source: String,
    val publishedAt: String,
    val urlToImage: String,
    val isBookmarked: Boolean,
    val updatedAt: Long = System.currentTimeMillis()
)


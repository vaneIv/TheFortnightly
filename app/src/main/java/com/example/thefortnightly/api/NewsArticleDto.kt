package com.example.thefortnightly.api

data class NewsArticleDto(
    val title: String?,
    val url: String,
    val content: String?,
    val description: String?,
    val category: String,
    val source: Source,
    val publishedAt: String,
    val urlToImage: String?
)

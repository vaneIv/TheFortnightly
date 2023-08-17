package com.example.thefortnightly.api

import com.example.thefortnightly.data.NewsArticle

data class NewsArticleDto(
    val title: String?,
    val url: String?,
    val content: String?,
    val description: String?,
    val source: Source,
    val publishedAt: String?,
    val urlToImage: String?
)

fun NewsArticleDto.asDomainCategoryArticle(category: String) = NewsArticle(
    title = title ?: "",
    url = url ?: "",
    content = content ?: "",
    description = description ?: "",
    source = source.name ?: "",
    urlToImage = urlToImage ?: "",
    publishedAt = publishedAt ?: "",
    category = category,
    isBookmarked = false
)
package com.example.thefortnightly.repo

import com.example.thefortnightly.api.NewsApiService
import com.example.thefortnightly.api.NewsArticleDto
import com.example.thefortnightly.data.NewsArticle
import com.example.thefortnightly.data.NewsArticleDatabase
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApiService,
    private val newsArticleDb: NewsArticleDatabase
) {

    private val newsArticleDao = newsArticleDb.newsArticleDao()

    suspend fun getTopHeadlines(): List<NewsArticle> {
        val response = newsApi.getTopHeadlines()
        val serverTopHeadlines = response.articles
        val topHeadlines = serverTopHeadlines.map { serverArticle ->
            NewsArticle(
                title = serverArticle.title ?: "",
                url = serverArticle.url ?: "",
                content = serverArticle.content ?: "",
                category = serverArticle.category ?: "",
                description = serverArticle.description ?: "",
                source = serverArticle.source.name ?: "",
                publishedAt = serverArticle.publishedAt ?: "",
                urlToImage = serverArticle.urlToImage ?: "",
                isBookmarked = false
            )
        }
        return topHeadlines
    }
}
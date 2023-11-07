package com.example.thefortnightly.repo

import androidx.room.withTransaction
import com.example.thefortnightly.api.NewsApiService
import com.example.thefortnightly.api.asDomainCategoryArticle
import com.example.thefortnightly.data.CategoryArticles
import com.example.thefortnightly.data.NewsArticle
import com.example.thefortnightly.data.NewsArticleDatabase
import com.example.thefortnightly.data.TopHeadlines
import com.example.thefortnightly.util.Resource
import com.example.thefortnightly.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApiService,
    private val newsArticleDb: NewsArticleDatabase
) {

    private val newsArticleDao = newsArticleDb.newsArticleDao()
    fun getTopHeadlines(
        category: String,
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchFailed: (Throwable) -> Unit
    ): Flow<Resource<List<NewsArticle>>> =
        networkBoundResource(
            query = {
                newsArticleDao.getBreakingNews()
            },
            fetch = {
                val response = newsApi.getTopHeadlines()
                response.articles
            },
            saveFetchResult = { serverBreakingNewsArticles ->
                val breakingNewsArticles =
                    serverBreakingNewsArticles.map { newsArticleDto ->
                        newsArticleDto.asDomainCategoryArticle(category)
                    }

                val breakingNews = breakingNewsArticles.map { article ->
                    TopHeadlines(article.url)
                }

                newsArticleDb.withTransaction {
                    newsArticleDao.deleteBreakingNews()
                    newsArticleDao.insertArticles(breakingNewsArticles)
                    newsArticleDao.insertBreakingNews(breakingNews)
                }
            },
            shouldFetch = { cachedArticles ->
                if (forceRefresh) {
                    true
                } else {
                    val sortedArticles = cachedArticles.sortedBy { newsArticle ->
                        newsArticle.updatedAt
                    }
                    val oldestTimestamp = sortedArticles.firstOrNull()?.updatedAt
                    val needsRefresh = oldestTimestamp == null ||
                            oldestTimestamp < System.currentTimeMillis() -
                            TimeUnit.HOURS.toMillis(1)

                    needsRefresh
                }
            },
            onFetchSuccess = onFetchSuccess,
            onFetchFailed = { t ->
                if (t !is HttpException && t !is IOException) {
                    throw t
                }
                onFetchFailed(t)
            }
        )

    fun getCategoryArticles(
        category: String,
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchFailed: (Throwable) -> Unit
    ): Flow<Resource<List<NewsArticle>>> =
        networkBoundResource(
            query = {
                newsArticleDao.getCategoryNews(category)
            },
            fetch = {
                val response = newsApi.getTopHeadlinesByCategory(category)
                response.articles
            },
            saveFetchResult = { serverCategoryArticles ->
                val categoryNewsArticles =
                    serverCategoryArticles.map { newsArticleDto ->
                        newsArticleDto.asDomainCategoryArticle(category)
                    }

                val categoryNews = categoryNewsArticles.map { article ->
                    CategoryArticles(article.url, article.category)
                }

                newsArticleDb.withTransaction {
                    newsArticleDao.deleteCategoryArticles(category)
                    newsArticleDao.insertArticles(categoryNewsArticles)
                    newsArticleDao.insertCategoryArticles(categoryNews)
                }
            },
            shouldFetch = { cachedArticles ->
                if (forceRefresh) {
                    true
                } else {
                    val sortedArticles = cachedArticles.sortedBy { newsArticle ->
                        newsArticle.updatedAt
                    }
                    val oldestTimestamp = sortedArticles.firstOrNull()?.updatedAt
                    val needsRefresh = oldestTimestamp == null ||
                            oldestTimestamp < System.currentTimeMillis() -
                            TimeUnit.MINUTES.toMillis(5)

                    needsRefresh
                }
            },
            onFetchSuccess = onFetchSuccess,
            onFetchFailed = { t ->
                if (t !is HttpException && t !is IOException) {
                    throw t
                }
                onFetchFailed(t)
            }
        )

    fun getArticle(articleUrl: String) = newsArticleDao.getArticle(articleUrl)
    suspend fun deleteNonBookmarkedArticlesOlderThen(timestampInMillis: Long) {
        newsArticleDao.deleteNonBookmarkedArticlesOlderThen(timestampInMillis)
    }
}
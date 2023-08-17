package com.example.thefortnightly.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticleDao {

    @Query("SELECT * FROM top_headlines INNER JOIN news_articles ON articleUrl = url")
    fun getBreakingNews(): Flow<List<NewsArticle>>

    @Query("SELECT * FROM category_articles INNER JOIN news_articles ON articleUrl = url WHERE articleCategory = :category")
    fun getCategoryNews(category: String): Flow<List<NewsArticle>>

    @Query("SELECT * FROM news_articles WHERE isBookmarked = 1")
    fun getAllBookmarkedArticles(): Flow<List<NewsArticle>>

    @Query("SELECT * FROM news_articles WHERE url = :articleKey")
    fun getArticle(articleKey: String): Flow<NewsArticle>

    // This query is used only for testing purposes.
    @Query("SELECT * FROM news_articles")
    fun getNewsArticles(): Flow<List<NewsArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<NewsArticle>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreakingNews(breakingNews: List<TopHeadlines>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryArticles(categoryArticles: List<CategoryArticles>)

    @Update
    suspend fun updateArticle(article: NewsArticle)

    @Query("UPDATE news_articles SET isBookmarked = 0")
    suspend fun resetAllBookmarks()

    @Query("DELETE FROM top_headlines")
    suspend fun deleteBreakingNews()

    @Query("DELETE FROM category_articles WHERE articleCategory = :category")
    suspend fun deleteCategoryArticles(category: String)

    @Query("DELETE FROM news_articles WHERE updatedAt < :timestampInMillis AND isBookmarked = 0")
    suspend fun deleteNonBookmarkedArticlesOlderThen(timestampInMillis: Long)
}
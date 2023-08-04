package com.example.thefortnightly.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.thefortnightly.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NewsArticleDaoTest {

    private lateinit var newsArticleDatabase: NewsArticleDatabase
    private lateinit var newsArticleDao: NewsArticleDao

    private val articleSports = NewsArticle("title", "url1", "content", "description", "sports", "nbc", "publishedAt", "urlToImage", false, 3)
    private val articleBusiness = NewsArticle("title", "url2", "content", "description", "business", "nbc", "publishedAt", "urlToImage", false, 1)
    private val articleScience = NewsArticle("title", "url3", "content", "description", "science", "nbc", "publishedAt", "urlToImage", false, 1)
    private val articleGeneral = NewsArticle("title", "url4", "content", "description", "general", "nbc", "publishedAt", "urlToImage", false, 1)
    private val articleTechnology = NewsArticle("title", "url5", "content", "description", "technology", "nbc", "publishedAt", "urlToImage", true, 1)
    private val articleHealth = NewsArticle("title", "url6", "content", "description", "health", "nbc", "publishedAt", "urlToImage", false, 1)

    private val articles = listOf(articleSports, articleBusiness, articleScience, articleGeneral, articleTechnology, articleHealth)

    private val topHeadlinesSports = CategoryArticles(1, "url1", "sports")
    private val topHeadlinesBusiness = CategoryArticles(2, "url2", "business")
    private val topHeadlinesScience = CategoryArticles(3, "url3", "science")
    private val topHeadlinesSports2 = CategoryArticles(4, "url4", "sports")
    private val topHeadlinesBusiness2 = CategoryArticles(5, "url5", "business")
    private val topHeadlinesScience2 = CategoryArticles(6, "url6", "science")

    private val topCategoryHeadlines = listOf(topHeadlinesSports, topHeadlinesBusiness, topHeadlinesScience, topHeadlinesSports2, topHeadlinesBusiness2, topHeadlinesScience2)

    private val topHeadlines1 = TopHeadlines(1, "url1")
    private val topHeadlines2 = TopHeadlines(2, "url2")
    private val topHeadlines3 = TopHeadlines(3, "url3")
    private val topHeadlines4 = TopHeadlines(4, "url4")
    private val topHeadlines5 = TopHeadlines(5, "url5")
    private val topHeadlines6 = TopHeadlines(6, "url6")

    private val topHeadlines = listOf(topHeadlines1, topHeadlines2, topHeadlines3, topHeadlines4, topHeadlines5, topHeadlines6)

    @get: Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        newsArticleDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NewsArticleDatabase::class.java
        ).build()

        newsArticleDao = newsArticleDatabase.newsArticleDao()
    }

    @After
    fun cleanup() {
        newsArticleDatabase.close()
    }

    @Test
    fun testInsertAndGetBreakingNews() = runTest {
        newsArticleDao.insertBreakingNews(topHeadlines)
        newsArticleDao.insertArticles(articles)

        newsArticleDao.getBreakingNews().test {
            val list = awaitItem()
            assertThat(list).containsExactly(
                articleSports, articleBusiness, articleScience, articleGeneral, articleTechnology, articleHealth
            )
            cancel()
        }
    }

    @Test
    fun testDeleteAllBreakingNewsItems() = runTest {
        newsArticleDao.insertBreakingNews(topHeadlines)
        newsArticleDao.insertArticles(articles)

        newsArticleDao.getBreakingNews().test {
            val list = awaitItem()
            assertThat(list).containsExactly(
                articleSports, articleBusiness, articleScience, articleGeneral, articleTechnology, articleHealth
            )
            cancel()
        }

        newsArticleDao.deleteBreakingNews()

        newsArticleDao.getBreakingNews().test {
            val list = awaitItem()
            assertThat(list).isEmpty()
        }
    }

    @Test
    fun testInsertAndGetCategoryArticles() = runTest {
        newsArticleDao.insertArticles(articles)
        newsArticleDao.insertCategoryArticles(topCategoryHeadlines)

        newsArticleDao.getCategoryNews("sports").test {
            val list = awaitItem()
            assertThat(list).hasSize(2)
            assertThat(list).contains(articleSports)
            cancel()
        }

        newsArticleDao.getCategoryNews("business").test {
            val list = awaitItem()
            assertThat(list).hasSize(2)
            assertThat(list).contains(articleBusiness)
            cancel()
        }

        newsArticleDao.getCategoryNews("science").test {
            val list = awaitItem()
            assertThat(list).hasSize(2)
            assertThat(list).contains(articleScience)
            cancel()
        }
    }

    @Test
    fun testDeleteCategoryArticles() = runTest {
        newsArticleDao.insertCategoryArticles(topCategoryHeadlines)
        newsArticleDao.insertArticles(articles)

        newsArticleDao.getCategoryNews("sports").test {
            val list = awaitItem()
            assertThat(list).hasSize(2)
            assertThat(list).contains(articleSports)
            cancel()
        }

        newsArticleDao.deleteCategoryArticles("sports")

        newsArticleDao.getCategoryNews("sport").test {
            val list = awaitItem()
            assertThat(list).isEmpty()
            cancel()
        }
    }

    @Test
    fun testDeleteNonBookmarkedArticlesOlderThen() = runTest {
        newsArticleDao.insertArticles(articles)

        newsArticleDao.getNewsArticles().test {
            val list = awaitItem()
            assertThat(list).containsExactly(
                articleSports, articleBusiness, articleScience, articleGeneral, articleTechnology, articleHealth
            )
            cancel()
        }

        newsArticleDao.deleteNonBookmarkedArticlesOlderThen(2)

        newsArticleDao.getNewsArticles().test {
            val list = awaitItem()
            assertThat(list).hasSize(2)
            assertThat(list).containsExactly(articleSports, articleTechnology)
            cancel()
        }
    }

    @Test
    fun testGetAllBookmarkedArticles() = runTest {
        newsArticleDao.insertArticles(articles)

        newsArticleDao.getAllBookmarkedArticles().test {
            val list = awaitItem()
            assertThat(list).hasSize(1)
            assertThat(list).contains(articleTechnology)
            cancel()
        }
    }

    @Test
    fun testResetAllBookmarkedArticlesToFalse() = runTest {
        newsArticleDao.insertArticles(articles)

        newsArticleDao.resetAllBookmarks()

        newsArticleDao.getAllBookmarkedArticles().test {
            val list = awaitItem()
            assertThat(list).isEmpty()
        }
    }
}
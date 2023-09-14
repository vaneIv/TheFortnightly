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

    private val articleSports = NewsArticle("title", "url1", "content", "description","nbc", "publishedAt", "urlToImage", "sports",false, 3)
    private val articleBusiness = NewsArticle("title", "url2", "content", "description","nbc", "publishedAt", "urlToImage", "business",false, 1)
    private val articleScience = NewsArticle("title", "url3", "content", "description", "nbc", "publishedAt", "urlToImage", "science", false, 1)
    private val articleGeneral = NewsArticle("title", "url4", "content", "description", "nbc", "publishedAt", "urlToImage", "general", false, 1)
    private val articleTechnology = NewsArticle("title", "url5", "content", "description", "nbc", "publishedAt", "urlToImage", "technology", true, 1)
    private val articleHealth = NewsArticle("title", "url6", "content", "description", "nbc", "publishedAt", "urlToImage", "health", false, 1)

    private val articles = listOf(articleSports, articleBusiness, articleScience, articleGeneral, articleTechnology, articleHealth)

    private val topHeadlinesSports = CategoryArticles("url1", "sports", 1)
    private val topHeadlinesBusiness = CategoryArticles("url2", "business", 2)
    private val topHeadlinesScience = CategoryArticles("url3", "science", 3)
    private val topHeadlinesSports2 = CategoryArticles("url4", "sports", 4)
    private val topHeadlinesBusiness2 = CategoryArticles("url5", "business", 5)
    private val topHeadlinesScience2 = CategoryArticles("url6", "science", 6)

    private val topCategoryHeadlines = listOf(topHeadlinesSports, topHeadlinesBusiness, topHeadlinesScience, topHeadlinesSports2, topHeadlinesBusiness2, topHeadlinesScience2)

    private val topHeadlines1 = TopHeadlines("url1", 1)
    private val topHeadlines2 = TopHeadlines("url2", 2)
    private val topHeadlines3 = TopHeadlines("url3", 3)
    private val topHeadlines4 = TopHeadlines("url4", 4)
    private val topHeadlines5 = TopHeadlines("url5", 5)
    private val topHeadlines6 = TopHeadlines("url6", 6)

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
    fun `testInsertAndGetBreakingNews`() = runTest {
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
    fun `testDeleteAllBreakingNewsItems`() = runTest {
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
            cancel()
        }
    }

    @Test
    fun `testInsertAndGetCategoryArticles`() = runTest {
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
    fun `testDeleteCategoryArticles`() = runTest {
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
    fun `testDeleteNonBookmarkedArticlesOlderThen`() = runTest {
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
    fun `testGetAllBookmarkedArticles`() = runTest {
        newsArticleDao.insertArticles(articles)

        newsArticleDao.getAllBookmarkedArticles().test {
            val list = awaitItem()
            assertThat(list).hasSize(1)
            assertThat(list).contains(articleTechnology)
            cancel()
        }
    }

    @Test
    fun `testResetAllBookmarkedArticlesToFalse`() = runTest {
        newsArticleDao.insertArticles(articles)

        newsArticleDao.resetAllBookmarks()

        newsArticleDao.getAllBookmarkedArticles().test {
            val list = awaitItem()
            assertThat(list).isEmpty()
            cancel()
        }
    }
}
package com.example.thefortnightly.repo

import androidx.room.withTransaction
import app.cash.turbine.test
import app.cash.turbine.testIn
import com.example.thefortnightly.R
import com.example.thefortnightly.api.NewsApiService
import com.example.thefortnightly.api.NewsArticleDto
import com.example.thefortnightly.api.NewsResponse
import com.example.thefortnightly.api.Source
import com.example.thefortnightly.data.CategoryArticles
import com.example.thefortnightly.data.NewsArticle
import com.example.thefortnightly.data.NewsArticleDao
import com.example.thefortnightly.data.NewsArticleDatabase
import com.example.thefortnightly.data.TopHeadlines
import com.example.thefortnightly.util.MainDispatcherRule
import com.example.thefortnightly.util.Resource
import com.example.thefortnightly.util.TimeUtil
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalCoroutinesApi::class)

class NewsRepositoryTest {

    private lateinit var repository: NewsRepository

    private lateinit var dao: NewsArticleDao

    @get: Rule
    val dispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var networkService: NewsApiService

    @MockK
    lateinit var database: NewsArticleDatabase

    @MockK
    lateinit var timeUtil: TimeUtil


    private val articleSports = NewsArticle(
        "title",
        "url1",
        "content",
        "description",
        "nbc",
        "publishedAt",
        "urlToImage",
        "sports",
        false,
        1
    )
    private val articleBusiness = NewsArticle(
        "title",
        "url2",
        "content",
        "description",
        "nbc",
        "publishedAt",
        "urlToImage",
        "business",
        false,
        1
    )
    private val articleScience = NewsArticle(
        "title",
        "url3",
        "content",
        "description",
        "nbc",
        "publishedAt",
        "urlToImage",
        "science",
        false,
        1
    )

    private val articles = listOf(articleSports)

    private val topHeadlinesSports = CategoryArticles("url1", "sports", 1)
    private val topHeadlinesBusiness = CategoryArticles("url2", "business", 2)
    private val topHeadlinesScience = CategoryArticles("url3", "science", 3)

    private val categoryArticles =
        listOf(topHeadlinesSports)

    private val topHeadlines1 = TopHeadlines("url1", 1)
    private val topHeadlines2 = TopHeadlines("url2", 2)
    private val topHeadlines3 = TopHeadlines("url3", 3)

    private val topHeadlines = listOf(topHeadlines1)

    private val newsResponse = NewsResponse(
        listOf(
            NewsArticleDto(
                "title",
                "url",
                "content",
                "description",
                Source("id", "nbc"),
                "publishedAt",
                "urlToImage"
            )
        )
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        mockkStatic(
            "androidx.room.RoomDatabaseKt"
        )

        val transactionLambda = slot<suspend () -> R>()
        coEvery { database.withTransaction(capture(transactionLambda)) } coAnswers {
            transactionLambda.captured.invoke()
        }

        repository = NewsRepository(networkService, database)
        dao = database.newsArticleDao()
    }

    @After
    fun cleanup() {
        database.close()
    }

    @Test
    fun `givenCachedArticlesExistAndNetworkServiceReturnErrorWhenGetArticlesIsCalledThenErrorResourceReturnedWithCachedArticles`() {
        runTest {
            every {
                dao.getBreakingNews()
            } answers { flow { emit(articles) } }

            every { timeUtil.getCurrentSystemTime() } answers { TimeUnit.HOURS.toMillis(3) }

            coEvery { networkService.getTopHeadlines() } throws IOException()

            repository.getTopHeadlines("", false, {}, {}).test {
                val result = awaitItem()
                assertThat(result).isInstanceOf(Resource.Error::class.java)
                assertThat(result.data).isEqualTo(articles)
                awaitComplete()
            }
        }
    }

    @Test
    fun proba() {
        runTest {
            every {
                dao.getBreakingNews()
            } answers { flow { emit(articles) } }

            every { timeUtil.getCurrentSystemTime() } answers { articleSports.updatedAt }

            coEvery { networkService.getTopHeadlines() } answers { newsResponse }

            repository.getTopHeadlines("sports", false, {}, {}).test {
                val result = awaitItem()
                assertThat(result).isInstanceOf(Resource.Loading::class.java)
                assertThat(result.data).isEqualTo(articles)
                awaitComplete()
            }
        }
    }

//    @Test
//    fun `givenCachedArticlesExistAndNetworkServiceReturnErrorWhenGetArticlesIsCalledThenErrorResourceReturnedWithCachedArticles`() =
//        runTest {
//
//            every { dao.getBreakingNews() } returns flow { emit(articles) }
//            every { timeUtil.getCurrentSystemTime() } returns TimeUnit.HOURS.toMillis(3)
//            coEvery { networkService.getTopHeadlines() } coAnswers { throw IOException() }
//
//            val resultFlow = repository.getTopHeadlines("", false, {}, {})
//
//            val result = resultFlow.test {
//                awaitItem()
//            }
//
//            assertThat(result).isInstanceOf(Resource.Error::class.java)
//            assertThat((result ).data).isEqualTo(articles)
//        }


}
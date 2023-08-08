package com.example.thefortnightly.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
    }

    @GET("top-headlines?country=us&pageSize=100")
    suspend fun getTopHeadlines(): NewsResponse

    @GET("top-headlines?country=us&pageSize=100")
    suspend fun getTopHeadlinesByCategory(@Query("category") category: String): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsResponse
}
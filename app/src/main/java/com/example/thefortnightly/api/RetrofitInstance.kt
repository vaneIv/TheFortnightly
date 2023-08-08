package com.example.thefortnightly.api

import com.example.thefortnightly.api.NewsApiService.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val newsApi by lazy {
            retrofit.create(NewsApiService::class.java)
        }
    }
}
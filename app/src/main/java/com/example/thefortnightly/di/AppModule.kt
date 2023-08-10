package com.example.thefortnightly.di

import android.content.Context
import androidx.room.Room
import com.example.thefortnightly.api.NewsApiService
import com.example.thefortnightly.api.NewsApiService.Companion.BASE_URL
import com.example.thefortnightly.data.NewsArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApiService =
        retrofit.create(NewsApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): NewsArticleDatabase =
        Room.databaseBuilder(appContext, NewsArticleDatabase::class.java, "news_article.db")
            .fallbackToDestructiveMigration()
            .build()
}
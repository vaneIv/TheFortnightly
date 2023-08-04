package com.example.thefortnightly.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        NewsArticle::class,
        TopHeadlines::class,
        CategoryArticles::class,
        SearchResult::class
    ],
    version = 1
)
abstract class NewsArticleDatabase : RoomDatabase() {

    abstract fun newsArticleDao(): NewsArticleDao

}
package com.example.thefortnightly.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "news_articles")
data class NewsArticle(
    val title: String,
    @PrimaryKey val url: String,
    val content: String,
    val description: String,
    val source: String,
    val publishedAt: String,
    val urlToImage: String,
    val category: String,
    val isBookmarked: Boolean,
    val updatedAt: Long = System.currentTimeMillis()
) : Parcelable



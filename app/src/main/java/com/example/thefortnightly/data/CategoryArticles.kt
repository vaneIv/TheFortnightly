package com.example.thefortnightly.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_articles")
data class CategoryArticles(
    val articleUrl: String,
    val articleCategory: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

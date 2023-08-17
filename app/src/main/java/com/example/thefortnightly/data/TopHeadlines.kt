package com.example.thefortnightly.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_headlines")
data class TopHeadlines(
    val articleUrl: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

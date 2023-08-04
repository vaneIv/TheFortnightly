package com.example.thefortnightly.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_headlines")
data class TopHeadlines(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val articleUrl: String
)

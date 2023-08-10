package com.example.thefortnightly.ui.shared

import androidx.recyclerview.widget.DiffUtil
import com.example.thefortnightly.data.NewsArticle

object NewsArticlesDiffCallback : DiffUtil.ItemCallback<NewsArticle>() {
    override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
        return oldItem == newItem
    }
}
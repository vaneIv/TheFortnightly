package com.example.thefortnightly.ui.shared

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.thefortnightly.data.NewsArticle
import com.example.thefortnightly.databinding.ListItemNewsArticleBinding

class NewsArticleAdapter :
    ListAdapter<NewsArticle, NewsArticleViewHolder>(NewsArticlesDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticleViewHolder {
        val binding = ListItemNewsArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsArticleViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }
}
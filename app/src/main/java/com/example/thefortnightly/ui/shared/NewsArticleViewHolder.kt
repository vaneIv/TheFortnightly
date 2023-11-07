package com.example.thefortnightly.ui.shared

import com.example.thefortnightly.data.NewsArticle
import com.example.thefortnightly.databinding.ListItemNewsArticleBinding

class NewsArticleViewHolder(
    private val binding: ListItemNewsArticleBinding,
    listener: NewsArticleAdapter.ArticleAdapterListener
) : BaseViewHolder<NewsArticle>(binding.root) {

    override fun bind(item: NewsArticle) {
        binding.article = item
    }

    init {
        binding.apply {
            this.listener = listener
        }
    }
}
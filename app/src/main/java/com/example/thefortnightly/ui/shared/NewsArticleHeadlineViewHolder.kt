package com.example.thefortnightly.ui.shared

import com.example.thefortnightly.data.NewsArticle
import com.example.thefortnightly.databinding.HeadlineListItemNewsArticleBinding

class NewsArticleHeadlineViewHolder(
    private val binding: HeadlineListItemNewsArticleBinding,
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
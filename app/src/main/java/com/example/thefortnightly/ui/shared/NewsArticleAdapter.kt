package com.example.thefortnightly.ui.shared

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.thefortnightly.data.NewsArticle
import com.example.thefortnightly.databinding.HeadlineListItemNewsArticleBinding
import com.example.thefortnightly.databinding.ListItemNewsArticleBinding

class NewsArticleAdapter(private val listener: ArticleAdapterListener) :
    ListAdapter<NewsArticle, BaseViewHolder<NewsArticle>>(NewsArticlesDiffCallback) {

    companion object {
        private const val TYPE_HEADLINE = 0
        private const val TYPE_ARTICLE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<NewsArticle> {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_HEADLINE -> NewsArticleHeadlineViewHolder(
                HeadlineListItemNewsArticleBinding.inflate(
                    inflater,
                    parent,
                    false
                ),
                listener
            )

            TYPE_ARTICLE -> NewsArticleViewHolder(
                ListItemNewsArticleBinding.inflate(
                    inflater,
                    parent,
                    false
                ),
                listener
            )

            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<NewsArticle>, position: Int) {
        val newsArticle = getItem(position)
        newsArticle?.let { holder.bind(newsArticle) }
//        holder.bind(getItem(position))
//        val currentItem = getItem(position)
//
//        currentItem?.let { holder.bind(currentItem) }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADLINE
            else -> TYPE_ARTICLE
        }
    }

    interface ArticleAdapterListener {
        fun onArticleClicked(view: View, article: NewsArticle)
    }
}
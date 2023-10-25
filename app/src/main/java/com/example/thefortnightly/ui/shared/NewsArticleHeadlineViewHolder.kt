package com.example.thefortnightly.ui.shared

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thefortnightly.R
import com.example.thefortnightly.data.NewsArticle
import com.example.thefortnightly.databinding.HeadlineListItemNewsArticleBinding

class NewsArticleHeadlineViewHolder(
    private val binding: HeadlineListItemNewsArticleBinding,
    private val onItemClick: (Int) -> Unit
) : BaseViewHolder<NewsArticle>(binding.root) {

    override fun bind(item: NewsArticle) {
        binding.apply {
            Glide.with(itemView)
                .load(item.urlToImage)
                .error(R.drawable.image_placeholder)
                .into(imageViewArticle)
        }
    }

    init {
        binding.root.setOnClickListener {
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClick(position)
            }
        }
    }
}
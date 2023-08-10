package com.example.thefortnightly.ui.shared

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thefortnightly.R
import com.example.thefortnightly.data.NewsArticle
import com.example.thefortnightly.databinding.ListItemNewsArticleBinding

class NewsArticleViewHolder(
    private val binding: ListItemNewsArticleBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NewsArticle) {
        binding.apply {
            Glide.with(itemView)
                .load(item.urlToImage)
                .error(R.drawable.image_placeholder)
                .into(imageViewArticle)

            textViewSource.text = item.source

            textViewTitle.text = item.title ?: ""
        }
    }
}
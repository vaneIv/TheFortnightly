package com.example.thefortnightly.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.thefortnightly.R
import com.example.thefortnightly.databinding.FragmentArticleDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment(R.layout.fragment_article_details) {

    private var _binding: FragmentArticleDetailsBinding? = null

    private val binding
        get() = _binding!!

    private val args: ArticleDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentArticleDetailsBinding.bind(view)

        binding.apply {
            Glide.with(view)
                .load(args.newsArticle.urlToImage)
                .error(R.drawable.image_placeholder)
                .into(imageViewArticle)

            textViewSource.text = args.newsArticle.source
            textViewTitle.text = args.newsArticle.title
            textViewContent.text = args.newsArticle.content
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.thefortnightly.ui.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefortnightly.R
import com.example.thefortnightly.databinding.FragmentBreakingNewsBinding
import com.example.thefortnightly.ui.NewsArticlesViewModel
import com.example.thefortnightly.ui.shared.NewsArticleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private val viewModel: NewsArticlesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentBreakingNewsBinding.bind(view)

        val newsArticleAdapter = NewsArticleAdapter()

        binding.apply {
            recyclerViewBreakingNews.apply {
                adapter = newsArticleAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.topHeadlines.collect { articles ->
                        newsArticleAdapter.submitList(articles)
                    }
                }
            }
        }
    }
}
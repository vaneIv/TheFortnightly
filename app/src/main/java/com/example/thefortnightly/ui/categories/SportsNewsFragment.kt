package com.example.thefortnightly.ui.categories

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefortnightly.R
import com.example.thefortnightly.databinding.FragmentSportsNewsBinding
import com.example.thefortnightly.ui.NewsArticlesViewModel
import com.example.thefortnightly.ui.shared.NewsArticleAdapter
import com.example.thefortnightly.util.Resource
import com.example.thefortnightly.util.exhaustive
import com.example.thefortnightly.util.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SportsNewsFragment : Fragment(R.layout.fragment_sports_news) {

    private val viewModel: NewsArticlesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSportsNewsBinding.bind(view)

        val newsArticleAdapter = NewsArticleAdapter()

        binding.apply {
            recyclerViewSportsNews.apply {
                adapter = newsArticleAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.sportsArticles.collect() {
                        val result = it ?: return@collect

                        swipeRefreshLayoutSportsNews.isRefreshing = result is Resource.Loading
                        recyclerViewSportsNews.isVisible = !result.data.isNullOrEmpty()
                        textViewError.isVisible =
                            result.error != null && result.data.isNullOrEmpty()
                        buttonRetry.isVisible = result.data != null && result.data.isNullOrEmpty()
                        textViewError.text = getString(
                            R.string.could_not_refresh,
                            result.error?.localizedMessage
                                ?: getString(R.string.unknown_error_occurred)
                        )

                        newsArticleAdapter.submitList(result.data)
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.events.collect { event ->
                        when (event) {
                            is NewsArticlesViewModel.Event.ShowErrorMessage ->
                                showSnackbar(
                                    getString(
                                        R.string.could_not_refresh,
                                        event.error.localizedMessage
                                            ?: getString(R.string.unknown_error_occurred)
                                    )
                                )
                        }.exhaustive
                    }
                }
            }

            swipeRefreshLayoutSportsNews.setOnRefreshListener {
                viewModel.onSportsCategoryRefresh()
            }

            buttonRetry.setOnClickListener {
                viewModel.onSportsCategoryRefresh()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.sportsCategoryOnStart()
    }
}
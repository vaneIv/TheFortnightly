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
import com.example.thefortnightly.databinding.FragmentScienceNewsBinding
import com.example.thefortnightly.ui.NewsArticlesViewModel
import com.example.thefortnightly.ui.shared.NewsArticleAdapter
import com.example.thefortnightly.util.Resource
import com.example.thefortnightly.util.exhaustive
import com.example.thefortnightly.util.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScienceNewsFragment : Fragment(R.layout.fragment_science_news) {

    private val viewModel: NewsArticlesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentScienceNewsBinding.bind(view)

        val newsArticleAdapter = NewsArticleAdapter()

        binding.apply {
            recyclerViewScienceNews.apply {
                adapter = newsArticleAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.scienceArticles.collect {
                        val result = it ?: return@collect

                        swipeRefreshLayoutScienceNews.isRefreshing = result is Resource.Loading
                        recyclerViewScienceNews.isVisible = !result.data.isNullOrEmpty()
                        textViewError.isVisible =
                            result.error != null && result.data.isNullOrEmpty()
                        buttonRetry.isVisible = result.error != null && result.data.isNullOrEmpty()
                        textViewError.text = getString(
                            R.string.could_not_refresh,
                            result.error?.localizedMessage
                                ?: getString(R.string.unknown_error_occurred)
                        )

                        newsArticleAdapter.submitList(result.data)
                        if (viewModel.pendingScrollToTopAfterRefresh) {
                            recyclerViewScienceNews.scrollToPosition(0)
                            viewModel.pendingScrollToTopAfterRefresh = false
                        }
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

            swipeRefreshLayoutScienceNews.setOnRefreshListener {
                viewModel.onScienceCategoryRefresh()
            }

            buttonRetry.setOnClickListener {
                viewModel.onScienceCategoryRefresh()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.scienceCategoryOnStart()
    }
}
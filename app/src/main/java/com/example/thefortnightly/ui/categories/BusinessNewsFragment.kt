package com.example.thefortnightly.ui.categories

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefortnightly.R
import com.example.thefortnightly.data.NewsArticle
import com.example.thefortnightly.databinding.FragmentBusinessNewsBinding
import com.example.thefortnightly.ui.NewsArticlesViewModel
import com.example.thefortnightly.ui.shared.BaseCategoryFragment
import com.example.thefortnightly.ui.shared.NewsArticleAdapter
import com.example.thefortnightly.ui.viewpager.HomeViewPagerFragmentDirections
import com.example.thefortnightly.util.Resource
import com.example.thefortnightly.util.addDividerDecoration
import com.example.thefortnightly.util.exhaustive
import com.example.thefortnightly.util.showSnackbar
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BusinessNewsFragment :
    BaseCategoryFragment<FragmentBusinessNewsBinding, NewsArticlesViewModel>(
        FragmentBusinessNewsBinding::inflate
    ), NewsArticleAdapter.ArticleAdapterListener {

    private val newsArticleAdapter = NewsArticleAdapter(this)

    override val viewModel: NewsArticlesViewModel by viewModels()

    override fun setupViews() {
        super.setupViews()

        binding.apply {
            recyclerViewBusinessNews.apply {
                adapter = newsArticleAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                addDividerDecoration(R.drawable.divider_horizontal)
            }

            buttonRetry.setOnClickListener {
                viewModel.onBusinessCategoryRefresh()
            }
        }
    }

    override fun setupVM() {
        super.setupVM()

        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            viewModel.businessArticles.collect {
                                val result = it ?: return@collect

                                swipeRefreshLayoutBusinessNews.isRefreshing =
                                    result is Resource.Loading
                                recyclerViewBusinessNews.isVisible = !result.data.isNullOrEmpty()
                                textViewError.isVisible =
                                    result.error != null && result.data.isNullOrEmpty()
                                buttonRetry.isVisible =
                                    result.error != null && result.data.isNullOrEmpty()
                                textViewError.text = getString(
                                    R.string.could_not_refresh,
                                    result.error?.localizedMessage
                                        ?: getString(R.string.unknown_error_occurred)
                                )

                                newsArticleAdapter.submitList(result.data) {
                                    if (viewModel.pendingScrollToTopAfterRefresh) {
                                        recyclerViewBusinessNews.scrollToPosition(0)
                                        viewModel.pendingScrollToTopAfterRefresh = false
                                    }
                                }
                            }
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

            swipeRefreshLayoutBusinessNews.setOnRefreshListener {
                viewModel.onBusinessCategoryRefresh()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.businessCategoryOnStart()
    }

    override fun onArticleClicked(view: View, article: NewsArticle) {
//        // Set exit and reenter transitions here as opposed to in onCreate because these transitions
//        // will be set and overwritten on HomeFragment for other navigation actions.
//        exitTransition = MaterialElevationScale(false).apply {
//            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
//        }
//        reenterTransition = MaterialElevationScale(true).apply {
//            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
//        }
        val articleDetailsTransitionName = getString(R.string.article_details_transition_name)
        val extras = FragmentNavigatorExtras(view to articleDetailsTransitionName)
        val directions =
            HomeViewPagerFragmentDirections.navigatePagerFragmentToDetailsDetailsFragment(article.url)
        findNavController().navigate(directions, extras)
    }
}
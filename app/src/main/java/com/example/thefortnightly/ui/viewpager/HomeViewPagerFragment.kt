package com.example.thefortnightly.ui.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import com.example.thefortnightly.R
import com.example.thefortnightly.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HomeViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        viewPager.adapter = NewsCategoriesPagerAdapter(this)

        // Set the text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition(1000, TimeUnit.MILLISECONDS)
        view.doOnPreDraw { startPostponedEnterTransition() }
        // Set exit and reenter transitions here as opposed to in onCreate because these transitions
        // will be set and overwritten on HomeFragment for other navigation actions.
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
    }
    private fun getTabTitle(position: Int): String? {
        return when (position) {
            BREAKING_NEWS_PAGE_INDEX -> getString(R.string.category_breaking_news_label)
            BUSINESS_NEWS_PAGE_INDEX -> getString(R.string.category_business_label)
            SPORTS_NEWS_PAGE_INDEX -> getString(R.string.category_sports_label)
            SCIENCE_NEWS_PAGE_INDEX -> getString(R.string.category_science_label)
            BOOKMARKS_NEWS_PAGE_INDEX -> getString(R.string.bookmarks_label)
            else -> null
        }
    }
}
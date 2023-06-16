package com.example.thefortnightly.ui.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.thefortnightly.ui.categories.BookmarksFragment
import com.example.thefortnightly.ui.categories.BreakingNewsFragment
import com.example.thefortnightly.ui.categories.BusinessNewsFragment
import com.example.thefortnightly.ui.categories.ScienceNewsFragment
import com.example.thefortnightly.ui.categories.SportsNewsFragment

const val BREAKING_NEWS_PAGE_INDEX = 0
const val BUSINESS_NEWS_PAGE_INDEX = 1
const val SPORTS_NEWS_PAGE_INDEX = 2
const val SCIENCE_NEWS_PAGE_INDEX = 3
const val BOOKMARKS_NEWS_PAGE_INDEX = 4

class NewsCategoriesPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

    /**
     * Mapping of the ViewPager page index to their respective fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        BREAKING_NEWS_PAGE_INDEX to { BreakingNewsFragment() },
        BUSINESS_NEWS_PAGE_INDEX to { BusinessNewsFragment() },
        SPORTS_NEWS_PAGE_INDEX to { SportsNewsFragment() },
        SCIENCE_NEWS_PAGE_INDEX to { ScienceNewsFragment() },
        BOOKMARKS_NEWS_PAGE_INDEX to { BookmarksFragment() }
    )
}
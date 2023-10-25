package com.example.thefortnightly.util

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.thefortnightly.R
import com.example.thefortnightly.data.NewsArticle
import com.example.thefortnightly.ui.viewpager.HomeViewPagerFragmentDirections
import com.google.android.material.snackbar.Snackbar

val <T> T.exhaustive: T
    get() = this

fun Fragment.showSnackbar(
    message: String,
    duration: Int = Snackbar.LENGTH_LONG,
    view: View = requireView()
) {
    Snackbar.make(view, message, duration).show()
}

fun RecyclerView.addDividerDecoration(
    drawableResourceId: Int,
    orientation: Int = RecyclerView.VERTICAL
) {
    val dividerItemDecoration = DividerItemDecoration(context, orientation)
    ResourcesCompat.getDrawable(resources, drawableResourceId, null)?.let {
        dividerItemDecoration.setDrawable(it)
        addItemDecoration(dividerItemDecoration)
    }
}

fun NavController.navigateToDetailsFragment(newsArticle: NewsArticle) {
    if (currentDestination?.id != R.id.homeViewPagerFragment) return
    navigate(HomeViewPagerFragmentDirections.navigatePagerFragmentToDetailsDetailsFragment(newsArticle))
}
package com.example.thefortnightly.util

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
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

//fun View.navigateToDetailsFragment(articleUrl: String, transitionNameResId: Int, fragment: Fragment) {
//    val transitionName = fragment.getString(transitionNameResId)
//    val extras = FragmentNavigatorExtras(this to transitionName)
//    val directions = HomeViewPagerFragmentDirections.navigatePagerFragmentToDetailsDetailsFragment(articleUrl)
//    findNavController().navigate(directions, extras)
//}

//fun NavController.navigateToDetailsFragment(newsArticle: NewsArticle) {
//    if (currentDestination?.id != R.id.homeViewPagerFragment) return
//    navigate(HomeViewPagerFragmentDirections.navigatePagerFragmentToDetailsDetailsFragment(newsArticle.url))
//}
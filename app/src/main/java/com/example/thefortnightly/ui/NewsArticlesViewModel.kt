package com.example.thefortnightly.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thefortnightly.data.NewsArticle
import com.example.thefortnightly.repo.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsArticlesViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val topHeadlinesFlow = MutableStateFlow<List<NewsArticle>>(emptyList())
    val topHeadlines: Flow<List<NewsArticle>> = topHeadlinesFlow

    init {
        viewModelScope.launch {
            val articles = repository.getTopHeadlines()
            topHeadlinesFlow.value = articles
        }
    }
}
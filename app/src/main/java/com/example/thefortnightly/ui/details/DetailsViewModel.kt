package com.example.thefortnightly.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.example.thefortnightly.repo.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    newsRepository: NewsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val article = savedStateHandle.getLiveData<String>("articleUrl").switchMap { articleUrl ->
        newsRepository.getArticle(articleUrl).asLiveData()
    }
}
package com.bangkit23.estetika.ui.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit23.estetika.data.Resources
import com.bangkit23.estetika.data.StorageRepository
import com.bangkit23.estetika.data.model.BatikArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val repository: StorageRepository): ViewModel() {

    private val _uiState = MutableStateFlow<ArticleUiState?>(null)
    val uiState: StateFlow<ArticleUiState?> = _uiState

    init {
        getAllData()
    }

    private fun getAllData() = viewModelScope.launch {
        repository.getBatikArticle().collect { resource ->
            _uiState.value = ArticleUiState(articleList = resource)
        }
    }
}

data class ArticleUiState(
    val articleList: Resources<List<BatikArticle>>
)
package com.bangkit23.estetika.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit23.estetika.data.AuthRepository
import com.bangkit23.estetika.data.Resources
import com.bangkit23.estetika.data.StorageRepository
import com.bangkit23.estetika.data.model.BatikArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val repository: StorageRepository
) : ViewModel() {

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

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getUser() = authRepository.hasUser()
}

data class ArticleUiState(
    val articleList: Resources<List<BatikArticle>>
)
package com.bangkit23.estetika.ui.destination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit23.estetika.data.Resources
import com.bangkit23.estetika.data.StorageRepository
import com.bangkit23.estetika.data.model.BatikTourism
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BatikDestinationViewModel @Inject constructor(private val repo: StorageRepository) : ViewModel() {


    private val _uiState = MutableStateFlow<BatikTourismUiState?>(null)
    val uiState: StateFlow<BatikTourismUiState?> = _uiState

    init {
        getAllData()
    }

    private fun getAllData() = viewModelScope.launch {
        repo.getBatikTourism().collect { resource ->
            _uiState.value = BatikTourismUiState(tourismList = resource)
        }
    }
}

data class BatikTourismUiState(
    val tourismList: Resources<List<BatikTourism>>
)
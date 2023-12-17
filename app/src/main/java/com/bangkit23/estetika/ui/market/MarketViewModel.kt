package com.bangkit23.estetika.ui.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit23.estetika.data.Resources
import com.bangkit23.estetika.data.StorageRepository
import com.bangkit23.estetika.data.model.BatikItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(private val repo: StorageRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ShopUiState?>(null)
    val uiState: StateFlow<ShopUiState?> = _uiState

    init {
        getShopData("batik_betawi")
    }

    private fun getShopData(col: String) = viewModelScope.launch {
        repo.getShopItems(col).collect { resource ->
            _uiState.value = ShopUiState(shopList = resource)
        }
    }
}

data class ShopUiState(
    val shopList: Resources<List<BatikItem>>
)
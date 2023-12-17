package com.bangkit23.estetika.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit23.estetika.data.AuthRepository
import com.bangkit23.estetika.data.model.UserRegister
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    fun register(email: String, password: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.register(email, password, onComplete)
        }
    }

    suspend fun userRegister(userRegister: UserRegister)  =
        repository.userRegister(userRegister)
}
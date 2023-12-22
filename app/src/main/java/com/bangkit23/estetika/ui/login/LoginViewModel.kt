package com.bangkit23.estetika.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit23.estetika.data.AuthRepository
import com.bangkit23.estetika.data.model.UserLogin
import com.bangkit23.estetika.data.remote.response.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    suspend fun userLogin(userLogin: UserLogin) : Flow<Result<LoginResponse>> =
        repository.userLogin(userLogin)

    fun saveAuthToken(token: String) {
        viewModelScope.launch {
            repository.saveAuthToken(token)
        }
    }
}
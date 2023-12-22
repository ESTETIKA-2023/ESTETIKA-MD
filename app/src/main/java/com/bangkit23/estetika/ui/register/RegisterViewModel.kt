package com.bangkit23.estetika.ui.register

import androidx.lifecycle.ViewModel
import com.bangkit23.estetika.data.AuthRepository
import com.bangkit23.estetika.data.model.UserRegister
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    suspend fun userRegister(userRegister: UserRegister)  =
        repository.userRegister(userRegister)
}
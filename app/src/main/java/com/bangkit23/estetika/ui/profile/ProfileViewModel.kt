package com.bangkit23.estetika.ui.profile

import androidx.lifecycle.ViewModel
import com.bangkit23.estetika.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    fun logout() = repository.logout()
}
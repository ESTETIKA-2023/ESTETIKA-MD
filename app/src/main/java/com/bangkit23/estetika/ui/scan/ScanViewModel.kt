package com.bangkit23.estetika.ui.scan

import androidx.lifecycle.ViewModel
import com.bangkit23.estetika.data.AuthRepository
import com.bangkit23.estetika.data.StorageRepository
import com.bangkit23.estetika.data.remote.response.UploadFileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val repository: StorageRepository
) : ViewModel() {

    fun getAuthToken(): Flow<String?> = authRepository.getAuthToken()
    suspend fun uploadImage(
        token: String,
        file: MultipartBody.Part
    ): Flow<Result<UploadFileResponse>> =
        repository.uploadImage(token, file)
}
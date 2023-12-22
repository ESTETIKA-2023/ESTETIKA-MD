package com.bangkit23.estetika.data

import com.bangkit23.estetika.data.local.AuthPreferencesDataSource
import com.bangkit23.estetika.data.model.UserLogin
import com.bangkit23.estetika.data.model.UserRegister
import com.bangkit23.estetika.data.remote.response.LoginResponse
import com.bangkit23.estetika.data.remote.response.RegisterResponse
import com.bangkit23.estetika.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val preferencesDataSource: AuthPreferencesDataSource
) {

    suspend fun userRegister(userRegister: UserRegister): Flow<Result<RegisterResponse>> = flow {
        try {
            val response = apiService.registerUser(userRegister)
            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun userLogin(userLogin: UserLogin): Flow<Result<LoginResponse>> = flow {
        try {
            val response = apiService.loginUser(userLogin)
            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun saveAuthToken(token: String) {
        preferencesDataSource.saveAuthToken(token)
    }

    /**
     * Get the user's authentication token from preferences
     *
     * @return Flow
     */
    fun getAuthToken(): Flow<String?> = preferencesDataSource.getAuthToken()
}
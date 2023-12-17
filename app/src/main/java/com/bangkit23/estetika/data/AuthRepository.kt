package com.bangkit23.estetika.data

import com.bangkit23.estetika.data.local.AuthPreferencesDataSource
import com.bangkit23.estetika.data.model.UserLogin
import com.bangkit23.estetika.data.model.UserRegister
import com.bangkit23.estetika.data.remote.response.LoginResponse
import com.bangkit23.estetika.data.remote.response.RegisterResponse
import com.bangkit23.estetika.data.remote.retrofit.ApiService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val apiService: ApiService,
    private val preferencesDataSource: AuthPreferencesDataSource
) {

    suspend fun login(email: String, password: String, onComplete: (Boolean) -> Unit) = withContext(Dispatchers.IO) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                onComplete.invoke(true)
            } else {
                onComplete.invoke(false)
            }
        }.await()
    }

    suspend fun register(email: String, password: String, onComplete: (Boolean) -> Unit) = withContext(Dispatchers.IO) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                onComplete.invoke(true)
            } else {
                onComplete.invoke(false)
            }
        }.await()
    }

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

    fun logout() = auth.signOut()

    fun hasUser():Boolean = auth.currentUser != null
}
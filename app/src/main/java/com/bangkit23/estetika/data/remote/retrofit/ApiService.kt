package com.bangkit23.estetika.data.remote.retrofit

import com.bangkit23.estetika.data.model.UserLogin
import com.bangkit23.estetika.data.model.UserRegister
import com.bangkit23.estetika.data.remote.response.LoginResponse
import com.bangkit23.estetika.data.remote.response.RegisterResponse
import com.bangkit23.estetika.data.remote.response.UploadFileResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    // Register Function
    @POST("register")
    suspend fun registerUser(@Body requestBodyBody: UserRegister): RegisterResponse

    // Login Function
    @POST("login")
    suspend fun loginUser(@Body requestBodyBody: UserLogin): LoginResponse

    // Predict Image Function
    @Multipart
    @POST("predict")
    suspend fun predictImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): UploadFileResponse
}
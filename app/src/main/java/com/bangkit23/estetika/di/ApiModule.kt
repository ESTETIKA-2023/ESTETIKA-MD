package com.bangkit23.estetika.di

import com.bangkit23.estetika.data.remote.retrofit.ApiConfig
import com.bangkit23.estetika.data.remote.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    /**
     * Provide API Service instance for Hilt
     *
     * @return ApiService
     */
    @Provides
    @Singleton
    fun provideApiService(): ApiService = ApiConfig.getApiService()
}



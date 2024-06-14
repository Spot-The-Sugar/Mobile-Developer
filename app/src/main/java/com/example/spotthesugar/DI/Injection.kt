package com.example.spotthesugar.DI

import com.example.spotthesugar.data.source.repository.AuthRepository
import com.example.spotthesugar.data.source.repository.ProfileRepository
import com.example.spotthesugar.data.source.service.ApiConfig

object Injection {
    fun provideRegisterRepository(): AuthRepository {
        val apiService = ApiConfig.getApiService()
        return AuthRepository.getInstance(apiService)
    }

    fun provideProfileRepository(): ProfileRepository{
        val apiService = ApiConfig.getApiService()
        return ProfileRepository.getInstance(apiService)
    }
}
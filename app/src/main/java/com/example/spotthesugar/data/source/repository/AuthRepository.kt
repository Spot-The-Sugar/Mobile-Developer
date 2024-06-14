package com.example.spotthesugar.data.source.repository

import androidx.lifecycle.liveData
import com.example.spotthesugar.data.ResultState
import com.example.spotthesugar.data.source.service.ApiService

class AuthRepository(private val apiService: ApiService) {

    fun login(email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.login(email, password)
            if (response.status != "success") {
                emit(ResultState.Error(response.message ?: "Unknown error"))
            }else{
                emit(ResultState.Success(response.data?.token?:""))
            }

        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun register(name: String, email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.register(name, email, password)
            if (response.status != "success") {
                emit(ResultState.Error(response.message ?: "Unknown error"))
            } else {
                emit(ResultState.Success(response))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(apiService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService)
            }.also { instance = it }
    }
}
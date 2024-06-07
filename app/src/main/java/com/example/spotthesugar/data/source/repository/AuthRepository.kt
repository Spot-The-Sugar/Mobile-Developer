package com.example.spotthesugar.data.source.repository

import androidx.lifecycle.liveData
import com.example.spotthesugar.data.ResultState
import com.example.spotthesugar.data.source.service.ApiService

class AuthRepository(private val apiService: ApiService) {

    fun login(email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.login(email, password)
            if (response.error!!) {
                emit(ResultState.Error(response.message!!))
            }

            emit(ResultState.Success(response.loginResult!!))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun register(name: String, email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.register(name, email, password)
            if (response.error == false) {
                emit(ResultState.Success(response))
            } else {
                emit(ResultState.Error(response.message ?: "Unknown error"))
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
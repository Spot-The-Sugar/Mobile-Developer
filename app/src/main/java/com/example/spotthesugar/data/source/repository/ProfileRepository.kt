package com.example.spotthesugar.data.source.repository

import androidx.lifecycle.liveData
import com.example.spotthesugar.data.ResultState
import com.example.spotthesugar.data.source.service.ApiService

class ProfileRepository(private val apiService: ApiService) {
    fun getProfile(bearerToken:String) = liveData {
        emit(ResultState.Loading)

        try {
            val response = apiService.profile(bearerToken)
            if (response.status != "success"){
                emit(ResultState.Error(response.message ?: "Unknown error"))
            }else{
                emit(ResultState.Success(response.profileResult))
            }
        }catch (e:Exception){
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun updateProfile(
        bearerToken: String,
        name:String,
        age:Int,
        weight:Double,
        height:Double,
        limit:Int
    )= liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.updateProfile(bearerToken, name, age, height, weight, limit)
            if (response.status != "success"){
                emit(ResultState.Error(response.message ?: "Unknown error"))
            }else{
                emit(ResultState.Success(response.message))
            }
        }catch (e:Exception){
            emit(ResultState.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: ProfileRepository? = null
        fun getInstance(apiService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: ProfileRepository(apiService)
            }.also { instance = it }
    }
}
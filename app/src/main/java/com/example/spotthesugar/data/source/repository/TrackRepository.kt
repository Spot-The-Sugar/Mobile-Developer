package com.example.spotthesugar.data.source.repository

import androidx.lifecycle.liveData
import com.example.spotthesugar.data.ResultState
import com.example.spotthesugar.data.source.service.ApiService

class TrackRepository(private val apiService: ApiService) {
    fun getHistories(bearerToken:String) = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getHistory(bearerToken)
            if (response.status != "success"){
                emit(ResultState.Error(response.message ?: "Unknown error"))
            }else{
                emit(ResultState.Success(response.data))
            }
        }catch (e:Exception){
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun getDetailHistory(bearerToken:String,id:Int) = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getDetailHistory(bearerToken,id)
            if (response.status != "success"){
                emit(ResultState.Error(response.message ?: "Unknown error"))
            }else{
                emit(ResultState.Success(response))
            }
        }catch (e:Exception){
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun getGrade(bearerToken:String,id:Int) = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getGrade(bearerToken,id)
            if (response.status != "success"){
                emit(ResultState.Error(response.message ?: "Unknown error"))
            }else{
                emit(ResultState.Success(response.data))
            }
        }catch (e:Exception){
            emit(ResultState.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: TrackRepository? = null
        fun getInstance(apiService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: TrackRepository(apiService)
            }.also { instance = it }
    }
}
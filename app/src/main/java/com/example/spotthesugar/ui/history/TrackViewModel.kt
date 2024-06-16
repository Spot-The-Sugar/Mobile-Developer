package com.example.spotthesugar.ui.history

import androidx.lifecycle.ViewModel
import com.example.spotthesugar.data.source.repository.TrackRepository

class TrackViewModel(private val repository: TrackRepository):ViewModel() {
    fun getHistories(bearerToken:String) = repository.getHistories(bearerToken)
    fun getDetailHistory(bearerToken:String,id:String) = repository.getDetailHistory(bearerToken,id)
}
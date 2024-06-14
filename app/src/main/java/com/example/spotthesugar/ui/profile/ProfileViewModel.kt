package com.example.spotthesugar.ui.profile

import androidx.lifecycle.ViewModel
import com.example.spotthesugar.data.source.repository.ProfileRepository

class ProfileViewModel(private val repository:ProfileRepository):ViewModel() {
    fun profile(bearerToken:String) = repository.getProfile(bearerToken)

    fun updateProfile(bearerToken: String,name:String,age:Int,weight:Double,height:Double,limit:Int)
    = repository.updateProfile(bearerToken,name,age,weight,height,limit)
}
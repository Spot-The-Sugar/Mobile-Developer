package com.example.spotthesugar.ui.login

import androidx.lifecycle.ViewModel
import com.example.spotthesugar.data.source.repository.AuthRepository

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {
    fun login(email:String,password:String) = repository.login(email, password)
}
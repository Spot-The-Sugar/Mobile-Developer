package com.example.spotthesugar.ui.signup

import androidx.lifecycle.ViewModel
import com.example.spotthesugar.data.source.repository.AuthRepository

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    fun register(name: String, email: String, password: String) =
        repository.register(name, email, password)

}
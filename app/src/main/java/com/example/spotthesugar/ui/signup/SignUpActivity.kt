package com.example.spotthesugar.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.spotthesugar.R
import com.example.spotthesugar.databinding.ActivitySignUpBinding
import com.example.spotthesugar.factory.ViewModelFactory
import com.example.spotthesugar.ui.login.LoginActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySignUpBinding
    private val viewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAction()
    }

    private fun setupAction() {
       binding.signInBtn.setOnClickListener {
           startActivity(Intent(this, LoginActivity::class.java))
       }


    }
}
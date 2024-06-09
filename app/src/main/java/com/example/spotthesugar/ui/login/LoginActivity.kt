package com.example.spotthesugar.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.spotthesugar.R
import com.example.spotthesugar.data.ResultState
import com.example.spotthesugar.data.pref.UserSharedPreference
import com.example.spotthesugar.databinding.ActivityLoginBinding
import com.example.spotthesugar.factory.ViewModelFactory
import com.example.spotthesugar.ui.MainActivity
import com.example.spotthesugar.ui.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance()
    }
    private val prefs: UserSharedPreference by lazy {
        val sharedPreferences =
            getSharedPreferences(UserSharedPreference.SHARED_PREFS, Context.MODE_PRIVATE)
        UserSharedPreference(sharedPreferences)
    }
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {

        binding.signUpBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            viewModel.login(email, password).observe(this@LoginActivity) { result ->
                when(result) {
                    is ResultState.Error -> {
                        showLoading(false)
                        showToast(result.error)
                    }
                    ResultState.Loading -> {
                        showLoading(true)
                    }
                    is ResultState.Success ->  {
                        Log.d("Token", result.data.token.toString())
                        prefs.saveAccessToken(result.data.token!!)
                        Log.d("Token disimpan", prefs.fetchAccessToken().toString())

                        Intent(this@LoginActivity, MainActivity::class.java).also {
                            startActivity(it)
                        }
                    }
                }
            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
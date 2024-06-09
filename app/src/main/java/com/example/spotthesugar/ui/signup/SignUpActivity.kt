package com.example.spotthesugar.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.spotthesugar.R
import com.example.spotthesugar.data.ResultState
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

       binding.signUpButton.setOnClickListener {
        val name = binding.name.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()
        val confirmPassword = binding.confirmPassword.text.toString().trim()

           if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
               viewModel.register(name, email, password).observe(this) { result ->
                   when (result) {
                       is ResultState.Loading -> {
                           showLoading(true)
                       }

                       is ResultState.Success -> {
                           showLoading(false)
                           showSuccessDialog(email)
                       }

                       is ResultState.Error -> {
                           showLoading(false)
                           showToast(result.error)
                       }
                   }
               }
           }
       }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showSuccessDialog(email: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage("Akun dengan $email sudah jadi nih. Yuk, login dan atur asupan gula harianmu.")
            setPositiveButton("Lanjut") { _, _ ->
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            create()
            show()
        }
    }
}
package com.example.spotthesugar.ui.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.spotthesugar.R
import com.example.spotthesugar.data.ResultState
import com.example.spotthesugar.data.pref.UserSharedPreference
import com.example.spotthesugar.databinding.ActivityEditProfileBinding
import com.example.spotthesugar.factory.ViewModelFactory

class EditProfileActivity : AppCompatActivity() {

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance()
    }
    private lateinit var binding: ActivityEditProfileBinding
//    private val prefs: UserSharedPreference by lazy {
//        val sharedPreferences =
//            getSharedPreferences(UserSharedPreference.SHARED_PREFS, Context.MODE_PRIVATE)
//        UserSharedPreference(sharedPreferences)
//    }
    private lateinit var prefs: UserSharedPreference
    //val token = prefs.fetchAccessToken()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(UserSharedPreference.SHARED_PREFS, Context.MODE_PRIVATE)
        prefs = UserSharedPreference(sharedPreferences)
        val token = prefs.fetchAccessToken().toString()

        getProfileData(token)
        Log.d("EditProfileActivity", "Token: $token")

        binding.updateBtn.setOnClickListener {
            val name = binding.name.text.toString()
            val age = binding.age.text.toString().toIntOrNull() ?: 0
            val height = binding.height.text.toString().toDoubleOrNull() ?: 0.0
            val weight = binding.weight.text.toString().toDoubleOrNull() ?: 0.0
            val limit = binding.limit.text.toString().toIntOrNull() ?: 0

            viewModel.updateProfile("Bearer $token",name, age, weight, height, limit).observe(this){ result ->
             when(result) {
                 is ResultState.Loading -> {
                     showLoading(true)
                 }
                 is ResultState.Success -> {
                     showLoading(false)
                     val intent = Intent()
                     setResult(RESULT_OK, intent)
                     finish()
                 }
                 is ResultState.Error -> {
                     showLoading(false)
                     Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                 }
             }

            }
        }
    }

    private fun getProfileData(token:String){
        viewModel.profile("Bearer $token").observe(this, Observer { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }
                is ResultState.Success -> {
                    showLoading(false)
                    val profile = result.data
                    binding.name.setText(profile?.userName)
                    binding.age.setText(profile?.userAge?.toString())
                    binding.height.setText(profile?.userHeight?.toString())
                    binding.weight.setText(profile?.userWeight?.toString())
                    binding.limit.setText(profile?.sugarLimit?.toString())
                }
                is ResultState.Error -> {
                    showLoading(false)
                    showToast(result.error)
                }
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
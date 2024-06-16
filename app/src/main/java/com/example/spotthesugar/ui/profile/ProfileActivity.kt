package com.example.spotthesugar.ui.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.spotthesugar.R
import com.example.spotthesugar.data.ResultState
import com.example.spotthesugar.data.pref.UserSharedPreference
import com.example.spotthesugar.databinding.ActivityProfileBinding
import com.example.spotthesugar.factory.ViewModelFactory
import com.example.spotthesugar.ui.MainActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding:ActivityProfileBinding
    private val viewModel by viewModels<ProfileViewModel>{
        ViewModelFactory.getInstance()
    }
//    private val prefs: UserSharedPreference by lazy {
//        val sharedPreferences =
//            getSharedPreferences(UserSharedPreference.SHARED_PREFS, Context.MODE_PRIVATE)
//        UserSharedPreference(sharedPreferences)
//    }
    private lateinit var prefs: UserSharedPreference
    private val editProfileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            loadProfileData()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(UserSharedPreference.SHARED_PREFS, Context.MODE_PRIVATE)
        prefs = UserSharedPreference(sharedPreferences)

        //observeViewModel()
        binding.editProfileImage.setOnClickListener {
            val intent = Intent(this,EditProfileActivity::class.java)
            editProfileLauncher.launch(intent)
        }

        loadProfileData()
        back()
    }

    private fun loadProfileData() {
        val token = prefs.fetchAccessToken()

        viewModel.profile("Bearer ${token.toString()}").observe(this, Observer { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }
                is ResultState.Success -> {
                    showLoading(false)
                    val profile = result.data
                    binding.profileNameDetail.text = profile?.userName
                    binding.profileEmailDetail.text = profile?.userEmail
                    binding.profileAge.text = profile?.userAge?.toString() ?: "null"
                    binding.profileWeight.text = profile?.userWeight?.toString() ?: "null"
                    binding.profileHeight.text = profile?.userHeight?.toString() ?: "null"
                    binding.profileSugarLimit.text = profile?.sugarLimit?.toString() ?: "null"
                }
                is ResultState.Error -> {
                    showLoading(false)
                    showToast("gagal update")
                }
            }
        })
    }

    private fun back(){
        binding.backButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeViewModel(){
        val token = prefs.fetchAccessToken()
        viewModel.profile("Bearer ${token.toString()}").observe(this@ProfileActivity){
            result ->
                when(result){
                    is ResultState.Loading -> showLoading(true)
                    is ResultState.Error ->{
                        showLoading(false)
                        showToast(result.error)
                    }
                    is ResultState.Success -> {
                        showLoading(false)
                        result.data.let { profile ->
                            binding.profileNameDetail.text = profile?.userName.toString()
                            binding.profileEmailDetail.text = profile?.userEmail.toString()
                            binding.profileAge.text = profile?.userAge.toString()
                            binding.profileWeight.text = profile?.userWeight.toString()
                            binding.profileHeight.text = profile?.userHeight.toString()
                            binding.profileSugarLimit.text = profile?.sugarLimit.toString()
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
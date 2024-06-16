package com.example.spotthesugar.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.spotthesugar.R
import com.example.spotthesugar.data.pref.UserSharedPreference
import com.example.spotthesugar.databinding.ActivityMainBinding
import com.example.spotthesugar.factory.ViewModelFactory
import com.example.spotthesugar.ui.camera.CameraActivity
import com.example.spotthesugar.ui.history.HistoryActivity
import com.example.spotthesugar.ui.login.LoginActivity
import com.example.spotthesugar.ui.profile.ProfileActivity
import com.example.spotthesugar.ui.profile.ProfileViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    private val prefs: UserSharedPreference by lazy {
        val sharedPreferences =
            getSharedPreferences(UserSharedPreference.SHARED_PREFS, Context.MODE_PRIVATE)
        UserSharedPreference(sharedPreferences)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showToast("Permission request granted")
            } else {
                showToast("Permission request denied")
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }


        binding.profilePicture.setOnClickListener {
            view ->
            showPopupMenu(view)
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.scanner_btn -> {
                    startCameraX()
                    true
                }
                R.id.history_btn -> {
                    startHistories()
                    true
                }
                else -> false
            }
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }

    private fun startHistories(){
        val intent = Intent(this,HistoryActivity::class.java)
        startActivity(intent)
    }

    private fun setGreetingText(){
        val username = prefs.fetchUsername() ?: "User"
        binding.greetingText.text = "Hi, $username \n Find, track and spot the sugar in your food"
    }

    private fun showPopupMenu(view:View){
        val popup = PopupMenu(this,view)
        val inflater:MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.main_menu,popup.menu)
        popup.setOnMenuItemClickListener { item:MenuItem ->
            when(item.itemId){
                R.id.profile_action -> {
                  startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                    true
                }
                R.id.logout_action -> {
                    logout()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun logout(){
        prefs.deleteAccessToken()
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

}
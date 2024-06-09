package com.example.spotthesugar.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import com.example.spotthesugar.R
import com.example.spotthesugar.data.pref.UserSharedPreference
import com.example.spotthesugar.databinding.ActivityMainBinding
import com.example.spotthesugar.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val prefs: UserSharedPreference by lazy {
        val sharedPreferences =
            getSharedPreferences(UserSharedPreference.SHARED_PREFS, Context.MODE_PRIVATE)
        UserSharedPreference(sharedPreferences)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profilePicture.setOnClickListener {
            view ->
            showPopupMenu(view)
        }
    }

    private fun showPopupMenu(view:View){
        val popup = PopupMenu(this,view)
        val inflater:MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.main_menu,popup.menu)
        popup.setOnMenuItemClickListener { item:MenuItem ->
            when(item.itemId){
                R.id.profile_action -> {
                  startActivity(Intent(this,ProfileActivity::class.java))
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
}
package com.example.spotthesugar.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.spotthesugar.R
import com.example.spotthesugar.data.pref.UserSharedPreference
import com.example.spotthesugar.ui.signup.SignUpActivity

class SplashActivity : AppCompatActivity() {

    private val prefs: UserSharedPreference by lazy {
        val sharedPreferences =
            getSharedPreferences(UserSharedPreference.SHARED_PREFS, Context.MODE_PRIVATE)
        UserSharedPreference(sharedPreferences)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            observeUser()
        }, 3000L)
    }

    private fun observeUser() {
        if(isUserAlreadyHere()) {
            Intent(this@SplashActivity, MainActivity::class.java).also {
                startActivity(it)
            }
        } else {
            Intent(this@SplashActivity, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun isUserAlreadyHere(): Boolean {
        val token = prefs.fetchAccessToken()
        return token != null
    }
}
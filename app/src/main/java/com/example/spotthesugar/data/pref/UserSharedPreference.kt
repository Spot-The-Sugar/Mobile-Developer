package com.example.spotthesugar.data.pref

import android.content.SharedPreferences

class UserSharedPreference(private val prefs:SharedPreferences) {

    fun saveAccessToken(token:String){
        prefs.edit().putString(ACCESS_TOKEN_KEY,token).apply()
    }

    fun fetchAccessToken():String?{
        return prefs.getString(ACCESS_TOKEN_KEY,null)
    }

    fun deleteAccessToken(){
        prefs.edit().putString(ACCESS_TOKEN_KEY,null).apply()
    }
    companion object{
        const val SHARED_PREFS = "APP_SHARED_PREFS"
        const val ACCESS_TOKEN_KEY = "access_token"
    }
}
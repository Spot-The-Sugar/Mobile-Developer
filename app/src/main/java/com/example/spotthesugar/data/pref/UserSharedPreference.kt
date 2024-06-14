package com.example.spotthesugar.data.pref

import android.content.SharedPreferences

class UserSharedPreference(private val prefs:SharedPreferences) {

    fun saveAccessToken(token:String){
        prefs.edit().putString(ACCESS_TOKEN_KEY,token).apply()
    }

    fun fetchAccessToken():String?{
        return prefs.getString(ACCESS_TOKEN_KEY,null)
    }

    fun fetchUsername():String?{
        return prefs.getString(USERNAME,null)
    }

    fun saveUsername(username:String){
        prefs.edit().putString(USERNAME,username).apply()
    }

    fun deleteAccessToken(){
        prefs.edit().putString(ACCESS_TOKEN_KEY,null).apply()
    }
    companion object{
        const val SHARED_PREFS = "APP_SHARED_PREFS"
        const val ACCESS_TOKEN_KEY = "access_token"
        const val USERNAME = "username"
    }
}
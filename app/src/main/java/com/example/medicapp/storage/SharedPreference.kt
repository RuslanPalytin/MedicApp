package com.example.medicapp.storage

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(private val context: Context) {

    companion object {
        private const val PREF_NAME_TOKEN = "token"
        private const val KEY_NAME_TOKEN = "TOKEN"
    }

    fun saveToken(token: String) {
        val sharePref: SharedPreferences =
            context.getSharedPreferences(PREF_NAME_TOKEN, Context.MODE_PRIVATE)
        sharePref.edit().putString(KEY_NAME_TOKEN, token).apply()
    }

    fun readToken(): String {
        val sharePref: SharedPreferences =
            context.getSharedPreferences(PREF_NAME_TOKEN, Context.MODE_PRIVATE)
        return sharePref.getString(KEY_NAME_TOKEN, null) ?: ""
    }
}
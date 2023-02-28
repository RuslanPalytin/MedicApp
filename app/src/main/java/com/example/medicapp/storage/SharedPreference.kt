package com.example.medicapp.storage

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(private val context: Context) {

    companion object {
        private const val PREF_NAME_TOKEN = "token"
        private const val KEY_NAME_TOKEN = "TOKEN"

        private const val PREF_NAME_EMAIL = "email"
        private const val KEY_NAME_EMAIL = "EMAIL"
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

    fun saveEmail(email: String) {
        val sharePref: SharedPreferences =
            context.getSharedPreferences(PREF_NAME_EMAIL, Context.MODE_PRIVATE)
        sharePref.edit().putString(KEY_NAME_EMAIL, email).apply()
    }

    fun readEmail(): String {
        val sharePref: SharedPreferences =
            context.getSharedPreferences(PREF_NAME_EMAIL, Context.MODE_PRIVATE)
        return sharePref.getString(KEY_NAME_EMAIL, null) ?: ""
    }
}
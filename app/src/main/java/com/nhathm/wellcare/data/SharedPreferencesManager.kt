package com.nhathm.wellcare.data

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    private const val PREF_NAME = "wellcare_preferences"
    private const val KEY_AUTH_TOKEN = "auth_token"
    private const val KEY_ROLE = "role"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
    }

    fun setAuthToken(authToken: String?) {
        sharedPreferences.edit().putString(KEY_AUTH_TOKEN, authToken).apply()
    }

    fun getRole(): String? {
        return sharedPreferences.getString(KEY_ROLE, null)
    }

    fun setRole(role: String?) {
        sharedPreferences.edit().putString(KEY_ROLE, role).apply()
    }
}
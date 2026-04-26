package com.example.habittracker.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveSession(username: String) {
        prefs.edit()
            .putString(KEY_USERNAME, username)
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)

    fun getLoggedInUsername(): String? = prefs.getString(KEY_USERNAME, null)

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val PREF_NAME = "session_prefs"
        private const val KEY_USERNAME = "key_username"
        private const val KEY_IS_LOGGED_IN = "key_is_logged_in"
    }
}
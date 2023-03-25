package com.example.todo.data.storage

import android.content.Context
import android.content.SharedPreferences

class SharedPrefStorageDataSource(context: Context) : StorageDataSource {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences("storage", 0)
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    override fun email(): String? {
        return stringPref("s_email")
    }

    override fun email(email: String) {
        stringPref("s_email", email)
    }

    override fun userId(): String? {
        return stringPref("s_user_id")
    }

    override fun userId(id: String) {
        stringPref("s_user_id", id)
    }

    override fun stringPref(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun stringPref(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

}
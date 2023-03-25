package com.example.todo.data.storage

interface StorageDataSource {
    fun clear()
    fun email(): String?
    fun email(email: String)
    fun userId(): String?
    fun userId(id: String)
    fun stringPref(key: String): String?
    fun stringPref(key: String, value: String)
}
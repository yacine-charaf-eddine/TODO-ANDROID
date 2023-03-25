package com.example.todo

import android.app.Application
import com.example.todo.data.storage.SharedPrefStorageDataSource
import com.example.todo.data.storage.StorageRepository

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        refreshSession(this)
    }

    private fun refreshSession(app: App) {
        val storageRepository = StorageRepository(SharedPrefStorageDataSource(app.applicationContext))
        storageRepository.refreshSession()
    }
}
package com.example.todo.data.storage

import com.example.todo.data.session.ToDoSession

class StorageRepository(private val storageDataSource: StorageDataSource) : StorageDataSource {

    fun refreshSession() {
        ToDoSession.INSTANCE.setEmail(email())
        ToDoSession.INSTANCE.setUserId(userId())
    }

    override fun clear() {
        storageDataSource.clear()
        ToDoSession.INSTANCE.clear()
    }

    override fun email(): String? {
        return this.storageDataSource.email()
    }

    override fun email(email: String) {
        this.storageDataSource.email(email)
        ToDoSession.INSTANCE.setEmail(email)
    }

    override fun userId(): String? {
        return this.storageDataSource.userId()
    }

    override fun userId(id: String) {
        storageDataSource.userId(id)
        ToDoSession.INSTANCE.setUserId(id)
    }

    override fun stringPref(key: String): String? {
        return storageDataSource.stringPref(key)
    }

    override fun stringPref(key: String, value: String) {
        storageDataSource.stringPref(key, value)
    }

}
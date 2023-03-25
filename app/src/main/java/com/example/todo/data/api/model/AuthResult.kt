package com.example.todo.data.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthResult(@SerializedName("email") @Expose private var email: String, @SerializedName("id") @Expose private var id: String) {
    fun getId(): String {
        return id
    }
    fun setId(id: String) {
        this.id = id
    }
    fun getEmail(): String {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }
}
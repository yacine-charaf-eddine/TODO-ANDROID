package com.example.todo.data.api.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SignUpRequest(@SerializedName("email") @Expose private var email: String) {
    fun getEmail(): String {
        return email
    }
}
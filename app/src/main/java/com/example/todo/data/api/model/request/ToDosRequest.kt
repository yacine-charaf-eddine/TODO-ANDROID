package com.example.todo.data.api.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlin.jvm.internal.Intrinsics

class ToDosRequest(@SerializedName("user_id") @Expose private var userId: String?) {

    fun getUserId(): String? {
        return userId
    }
}
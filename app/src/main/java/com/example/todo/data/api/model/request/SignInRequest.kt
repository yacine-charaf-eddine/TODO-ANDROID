package com.example.todo.data.api.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SignInRequest(@SerializedName("email") @Expose var identity: String) {

}
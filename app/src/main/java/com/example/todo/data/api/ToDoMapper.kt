package com.example.todo.data.api

import com.example.todo.data.api.model.AuthResult
import com.example.todo.data.api.model.UniversalResult
import retrofit2.Response

class ToDoMapper {
    fun <ITEM> processResponse(response: Response<ITEM>): UniversalResult<ITEM> {
        val message: String
        val universalResult: UniversalResult<ITEM> = UniversalResult(response.code(), response.message(), null, null)
        if (!response.isSuccessful) {
            message = if (response.code() == 500) {
                "Server has encountered an issue."
            } else {
                "API Error " + response.code() + " : " + response.message()
            }
            universalResult.message = message
            return universalResult
        }
        val body: ITEM? = response.body()
        if (body == null) {
            universalResult.message = "Empty Response : " + response.code() + " : " + response.message()
            return universalResult
        }
        universalResult.setItem(body)
        return universalResult
    }

    fun <ITEM> processAuthResponse(response: Response<List<ITEM>>): UniversalResult<ITEM> {
        val message: String
        val universalResult: UniversalResult<ITEM> = UniversalResult(response.code(), response.message(), null, null)
        if (!response.isSuccessful) {
            message = if (response.code() == 500) {
                "Server has encountered an issue."
            } else {
                "API Error " + response.code() + " : " + response.message()
            }
            universalResult.message = message
            return universalResult
        }
        val body: List<ITEM>? = response.body()
        if (body == null || body.isEmpty()) {
            universalResult.message = "user do not exist"
            return universalResult
        }
        universalResult.setItem(body[0])
        return universalResult
    }

    fun <ITEM> processListResponse(response: Response<List<ITEM>>): UniversalResult<ITEM> {
        val message: String
        val universalResult: UniversalResult<ITEM> = UniversalResult(response.code(), response.message(), null, null)
        if (!response.isSuccessful) {
            message = if (response.code() == 500) {
                "Server has encountered an issue."
            } else {
                "API Error " + response.code() + " : " + response.message()
            }
            universalResult.message = message
            return universalResult
        }
        val body: List<ITEM>? = response.body()
        if (body == null || body.isEmpty()) {
            universalResult.message = "no todos"
            return universalResult
        }
        universalResult.setItems(body)
        return universalResult
    }
}
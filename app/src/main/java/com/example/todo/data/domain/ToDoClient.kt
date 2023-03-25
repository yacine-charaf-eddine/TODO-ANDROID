package com.example.todo.data.domain

import com.example.todo.data.api.model.AuthResult
import com.example.todo.data.api.model.request.SignUpRequest
import com.example.todo.data.api.model.request.ToDosRequest
import com.example.todo.ui.toDo.SubToDo
import com.example.todo.ui.toDo.ToDo
import retrofit2.Response
import retrofit2.http.*

interface ToDoClient {

    @GET("authentification")
    suspend fun signIn(@Query("email") email: String): Response<List<AuthResult>>

    @POST("authentification")
    suspend fun signUp(@Body emailSignUpRequest: SignUpRequest): Response<AuthResult>

    @GET("todos")
    suspend fun fetchToDos(@Query("userId") userId: String?): Response<List<ToDo>>

    @GET("nestedTodos")
    suspend fun fetchSubToDos(@Query("todoId") id: String?): Response<List<SubToDo>>

    @POST("todos")
    suspend fun addToDo(@Body toDo: ToDo): Response<ToDo>

    @PATCH ("todos/{id}")
    suspend fun updateToDo(@Path("id") id: String?, @Body toDo: ToDo): Response<ToDo>

    @DELETE("todos/{id}")
    suspend fun deleteToDo(@Path("id") id: String?): Response<ToDo>

    @POST("nestedTodos")
    suspend fun addSubToDo(@Body toDo: SubToDo): Response<SubToDo>

    @PATCH("nestedTodos/{id}")
    suspend fun updateSubToDo(@Path("id") id: String?, @Body subToDo: SubToDo): Response<SubToDo>

    @DELETE("nestedTodos/{id}")
    suspend fun deleteSubToDo(@Path("id") id: String?): Response<SubToDo>

    @GET("authentification")
    suspend fun fetchEmailsList(): Response<List<AuthResult>>
}
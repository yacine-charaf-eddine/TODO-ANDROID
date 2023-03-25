package com.example.todo.data.source

import com.example.todo.data.api.model.AuthResult
import com.example.todo.data.api.model.request.SignInRequest
import com.example.todo.data.api.model.request.SignUpRequest
import com.example.todo.data.api.model.UniversalResult
import com.example.todo.data.api.model.request.ToDosRequest
import com.example.todo.ui.toDo.SubToDo
import com.example.todo.ui.toDo.ToDo

interface ToDoDataSource {
    suspend fun signIn(signInRequest: SignInRequest): UniversalResult<AuthResult>
    suspend fun signUp(emailSignUpRequest: SignUpRequest): UniversalResult<AuthResult>
    suspend fun fetchToDos(): UniversalResult<ToDo>
    suspend fun fetchSubToDos(id: String?): UniversalResult<SubToDo>
    suspend fun addToDo(toDo: ToDo): UniversalResult<ToDo>
    suspend fun deleteToDo(id: String?): UniversalResult<ToDo>
    suspend fun addSubToDo(subToDo: SubToDo): UniversalResult<SubToDo>
    suspend fun updateToDo(toDo: ToDo): UniversalResult<ToDo>
    suspend fun updateSubToDo(subToDo: SubToDo): UniversalResult<SubToDo>
    suspend fun deleteSubToDo(id: String?): UniversalResult<SubToDo>
    suspend fun fetchEmailsList(): UniversalResult<AuthResult>
}
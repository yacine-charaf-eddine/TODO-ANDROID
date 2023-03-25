package com.example.todo.data.source

import com.example.todo.data.api.model.AuthResult
import com.example.todo.data.api.model.request.SignInRequest
import com.example.todo.data.api.model.request.SignUpRequest
import com.example.todo.data.api.model.UniversalResult
import com.example.todo.ui.toDo.SubToDo
import com.example.todo.ui.toDo.ToDo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ToDoRepository(private val toDoDataSource: ToDoDataSource) : ToDoDataSource {

    override suspend fun signIn(signInRequest: SignInRequest): UniversalResult<AuthResult> {
        return withContext(Dispatchers.IO){
            return@withContext toDoDataSource.signIn(signInRequest)
        }
    }

    override suspend fun signUp(emailSignUpRequest: SignUpRequest): UniversalResult<AuthResult> {
        return withContext(Dispatchers.IO){
            return@withContext toDoDataSource.signUp(emailSignUpRequest)
        }
    }

    override suspend fun fetchToDos(): UniversalResult<ToDo> {
        return withContext(Dispatchers.IO){
            return@withContext toDoDataSource.fetchToDos()
        }
    }

    override suspend fun fetchSubToDos(id: String?): UniversalResult<SubToDo> {
        return withContext(Dispatchers.IO){
            return@withContext toDoDataSource.fetchSubToDos(id)
        }
    }

    override suspend fun addToDo(toDo: ToDo): UniversalResult<ToDo> {
        return withContext(Dispatchers.IO){
            return@withContext toDoDataSource.addToDo(toDo)
        }
    }

    override suspend fun deleteToDo(id: String?): UniversalResult<ToDo> {
        return withContext(Dispatchers.IO){
            return@withContext toDoDataSource.deleteToDo(id)
        }
    }

    override suspend fun addSubToDo(subToDo: SubToDo): UniversalResult<SubToDo> {
        return withContext(Dispatchers.IO){
            return@withContext toDoDataSource.addSubToDo(subToDo)
        }
    }

    override suspend fun updateToDo(toDo: ToDo): UniversalResult<ToDo> {
        return withContext(Dispatchers.IO){
            return@withContext toDoDataSource.updateToDo(toDo)
        }
    }

    override suspend fun updateSubToDo(subToDo: SubToDo): UniversalResult<SubToDo> {
        return withContext(Dispatchers.IO){
            return@withContext toDoDataSource.updateSubToDo(subToDo)
        }
    }

    override suspend fun deleteSubToDo(id: String?): UniversalResult<SubToDo> {
        return withContext(Dispatchers.IO){
            return@withContext toDoDataSource.deleteSubToDo(id)
        }
    }

    override suspend fun fetchEmailsList(): UniversalResult<AuthResult> {
        return withContext(Dispatchers.IO){
            return@withContext toDoDataSource.fetchEmailsList()
        }
    }
}
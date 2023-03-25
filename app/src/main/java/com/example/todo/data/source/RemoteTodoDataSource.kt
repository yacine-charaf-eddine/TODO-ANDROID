package com.example.todo.data.source

import android.util.Log
import com.example.todo.data.api.ToDoMapper
import com.example.todo.data.api.model.AuthResult
import com.example.todo.data.api.model.request.SignInRequest
import com.example.todo.data.api.model.request.SignUpRequest
import com.example.todo.data.api.model.UniversalResult
import com.example.todo.data.api.model.request.ToDosRequest
import com.example.todo.data.domain.ToDoClient
import com.example.todo.data.session.ToDoSession
import com.example.todo.ui.toDo.SubToDo
import com.example.todo.ui.toDo.ToDo
import retrofit2.Response

class RemoteTodoDataSource(private val toDoClient: ToDoClient?, private val toDoMapper: ToDoMapper) : ToDoDataSource {

    override suspend fun signIn(signInRequest: SignInRequest): UniversalResult<AuthResult> {
        val response : Response<List<AuthResult>> = toDoClient!!.signIn(signInRequest.identity)
        return toDoMapper.processAuthResponse(response)
    }

    override suspend fun signUp(emailSignUpRequest: SignUpRequest): UniversalResult<AuthResult> {
        val response : Response<AuthResult> = toDoClient!!.signUp(emailSignUpRequest)
        return toDoMapper.processResponse(response)
    }

    override suspend fun fetchToDos(): UniversalResult<ToDo> {
        val response : Response<List<ToDo>> = toDoClient!!.fetchToDos(ToDoSession.INSTANCE.getUserId())
        return toDoMapper.processListResponse(response)
    }

    override suspend fun fetchSubToDos(id: String?): UniversalResult<SubToDo> {
        val response : Response<List<SubToDo>> = toDoClient!!.fetchSubToDos(id)
        return toDoMapper.processListResponse(response)
    }

    override suspend fun addToDo(toDo: ToDo): UniversalResult<ToDo> {
        toDo.userId = ToDoSession.INSTANCE.getUserId()
        val response : Response<ToDo> = toDoClient!!.addToDo(toDo)
        return toDoMapper.processResponse(response)
    }

    override suspend fun updateToDo(toDo: ToDo): UniversalResult<ToDo> {
        val response : Response<ToDo> = toDoClient!!.updateToDo(toDo.id, toDo)
        return toDoMapper.processResponse(response)
    }

    override suspend fun deleteToDo(id: String?): UniversalResult<ToDo> {
        val response : Response<ToDo> = toDoClient!!.deleteToDo(id)
        return toDoMapper.processResponse(response)
    }

    override suspend fun addSubToDo(subToDo: SubToDo): UniversalResult<SubToDo> {
        val response : Response<SubToDo> = toDoClient!!.addSubToDo(subToDo)
        return toDoMapper.processResponse(response)
    }

    override suspend fun updateSubToDo(subToDo: SubToDo): UniversalResult<SubToDo> {
        val response : Response<SubToDo> = toDoClient!!.updateSubToDo(subToDo.id, subToDo)
        return toDoMapper.processResponse(response)
    }

    override suspend fun deleteSubToDo(id: String?): UniversalResult<SubToDo> {
        val response : Response<SubToDo> = toDoClient!!.deleteSubToDo(id)
        return toDoMapper.processResponse(response)
    }

    override suspend fun fetchEmailsList(): UniversalResult<AuthResult> {
        val response : Response<List<AuthResult>> = toDoClient!!.fetchEmailsList()
        return toDoMapper.processListResponse(response)
    }
}
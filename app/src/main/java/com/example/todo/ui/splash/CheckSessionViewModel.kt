package com.example.todo.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.session.ToDoSession
import com.example.todo.data.storage.StorageRepository
import com.example.todo.ui.toDo.SubToDo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CheckSessionViewModel() : ViewModel() {
    var showHomePageLiveData = MutableLiveData<Boolean>()
    var showAuthPromptPageLiveData = MutableLiveData<Boolean>()
    private val errorHandler : CoroutineExceptionHandler = CoroutineExceptionHandler{ _: CoroutineContext, throwable: Throwable -> run { throwable.printStackTrace() } }

    fun checkSession() {
        viewModelScope.launch(errorHandler) {
            if (ToDoSession.INSTANCE.isEmpty()) {
                showAuthPromptPageLiveData.value = true
            } else {
                showHomePageLiveData.value = true
            }
        }
    }
}
package com.example.todo.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.commons.recycleradapter.AdapterUpdate
import com.example.todo.data.api.model.UniversalResult
import com.example.todo.data.source.ToDoRepository
import com.example.todo.ui.toDo.SubToDo
import com.example.todo.ui.toDo.ToDo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(private val toDoRepository: ToDoRepository) : ViewModel() {
    var toDosLiveData = MutableLiveData<AdapterUpdate<ToDo>>()
    var subToDosLiveData = MutableLiveData<Pair<String?, List<SubToDo>>>()
    private val errorHandler : CoroutineExceptionHandler = CoroutineExceptionHandler{ _: CoroutineContext, throwable: Throwable -> run { throwable.printStackTrace() } }
    init {
        fetchToDos()
    }

    fun fetchToDos() {
        viewModelScope.launch(errorHandler){
            val result: UniversalResult<ToDo> = toDoRepository.fetchToDos()
            if (result.isSuccess() && result.hasItems()){
                toDosLiveData.value = AdapterUpdate(result.requireItems(), 0)
            }
        }
    }

    fun fetchSubToDos(toDo: ToDo?) {
        viewModelScope.launch(errorHandler){
            val result: UniversalResult<SubToDo> = toDoRepository.fetchSubToDos(toDo?.id)
            if (result.isSuccess() && result.hasItems()){
                subToDosLiveData.value = Pair(toDo?.id, result.requireItems().reversed())
            }
        }
    }
}
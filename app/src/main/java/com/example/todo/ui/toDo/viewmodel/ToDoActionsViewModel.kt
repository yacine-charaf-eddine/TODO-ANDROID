package com.example.todo.ui.toDo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todo.commons.viewmodel.BaseViewModel
import com.example.todo.data.api.model.UniversalResult
import com.example.todo.data.source.ToDoRepository
import com.example.todo.ui.toDo.SubToDo
import com.example.todo.ui.toDo.ToDo
import com.example.todo.ui.toDo.ToDoItem
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ToDoActionsViewModel(private val toDoRepository: ToDoRepository) : BaseViewModel() {
    var toDoAddedLiveData = MutableLiveData<ToDo>()
    var toDoDeletedLiveData = MutableLiveData<ToDo>()
    var toDoUpdatedLiveData = MutableLiveData<ToDo>()
    var subToDoAddedLiveData = MutableLiveData<SubToDo>()
    var subToDoDeletedLiveData = MutableLiveData<SubToDo>()
    var subToDoUpdatedLiveData = MutableLiveData<SubToDo>()
    private val errorHandler : CoroutineExceptionHandler = CoroutineExceptionHandler{ _: CoroutineContext, throwable: Throwable -> run { throwable.printStackTrace() } }

    fun addToDo(toDo: ToDo) {
        viewModelScope.launch(errorHandler){
            if (performValidation(toDo)){
                val result: UniversalResult<ToDo> = toDoRepository.addToDo(toDo)
                if (result.isSuccess() && result.hasItem()){
                    toDoAddedLiveData.value = result.requireItem()
                }
            }
        }
    }

    fun updateToDo(toDo: ToDo) {
        viewModelScope.launch(errorHandler){
            val result: UniversalResult<ToDo> = toDoRepository.updateToDo(toDo)
            if (result.isSuccess()){
                toDoUpdatedLiveData.value = result.requireItem()
            }else{
                toast(result.message)
            }
        }
    }

    fun deleteToDO(toDo: ToDo) {
        viewModelScope.launch(errorHandler){
            val result: UniversalResult<ToDo> = toDoRepository.deleteToDo(toDo.id)
            if (result.isSuccess()){
                toDoDeletedLiveData.value = toDo
            }else{
                toast(result.message)
            }
        }
    }

    fun addSubToDo(subToDo: SubToDo) {
        viewModelScope.launch(errorHandler){
            if (performValidation(subToDo)){
                val result: UniversalResult<SubToDo> = toDoRepository.addSubToDo(subToDo)
                if (result.isSuccess() && result.hasItem()){
                    subToDoAddedLiveData.value = result.requireItem()
                }
            }
        }
    }

    fun updateSubToDo(subToDo: SubToDo) {
        viewModelScope.launch(errorHandler){
            val result: UniversalResult<SubToDo> = toDoRepository.updateSubToDo(subToDo)
            if (result.isSuccess()){
                subToDoUpdatedLiveData.value = result.requireItem()
            }else{
                toast(result.message)
            }
        }
    }

    fun deleteSubToDO(subToDo: SubToDo) {
        viewModelScope.launch(errorHandler){
            val result: UniversalResult<SubToDo> = toDoRepository.deleteSubToDo(subToDo.id)
            if (result.isSuccess()){
                subToDoDeletedLiveData.value = subToDo
            }else{
                toast(result.message)
            }
        }
    }

    fun toggleCompleteToDo(toDo: ToDo) {
        viewModelScope.launch(errorHandler){
            toDo.completed = !toDo.completed!!
            val result: UniversalResult<ToDo> = toDoRepository.updateToDo(toDo)
            if (result.isSuccess()){
                toDoUpdatedLiveData.value = result.requireItem()
            }else{
                toast(result.message)
            }
        }
    }

    fun toggleCompleteSubToDo(subToDo: SubToDo) {
        viewModelScope.launch(errorHandler){
            subToDo.completed = !subToDo.completed!!
            val result: UniversalResult<SubToDo> = toDoRepository.updateSubToDo(subToDo)
            if (result.isSuccess()){
                subToDoUpdatedLiveData.value = result.requireItem()
            }else{
                toast(result.message)
            }
        }
    }

    private fun performValidation(toDoItem: ToDoItem): Boolean{
        if (toDoItem is ToDo){
            if(toDoItem.title != null && toDoItem.title!!.isNotBlank()){
                return true
            }
        }else if(toDoItem is SubToDo){
            if(toDoItem.title != null && toDoItem.title!!.isNotBlank()){
                return true
            }
        }
        toast("Please enter valid data")
        return false
    }
}
package com.example.todo.commons.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val toastLiveData = MutableLiveData<String>()

    fun getToastLiveData(): MutableLiveData<String> {
        return toastLiveData
    }

    fun toast(message: String) {
        viewModelScope.launch {
            getToastLiveData().value = message
        }
    }
}
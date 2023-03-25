package com.example.todo.commons.viewmodel

import androidx.lifecycle.ViewModel

interface ViewModelRegistrar {
    fun registerViewModels(vararg viewModelArr: ViewModel)
}
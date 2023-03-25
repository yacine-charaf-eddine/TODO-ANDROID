package com.example.todo.ui.home

import com.example.todo.ui.toDo.SubToDo
import com.example.todo.ui.toDo.ToDo

interface HomeActivityHandle {
    fun onAddToDoRequested(toDo: ToDo)
    fun onAddSubToDoRequested(subToDo: SubToDo)
    fun onUpdateToDoRequested(toDo: ToDo)
    fun onUpdateSubToDoRequested(subToDo: SubToDo)
}
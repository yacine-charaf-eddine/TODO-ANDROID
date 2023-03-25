package com.example.todo.ui.toDo.adapter.payload

import com.example.todo.ui.toDo.ToDo

abstract class ToDoPayload() {
    var id: String? = null
    constructor(id: String) : this() { this.id = id }
    class ToDoUpdated(id: String,val toDo: ToDo) : ToDoPayload(id)
}
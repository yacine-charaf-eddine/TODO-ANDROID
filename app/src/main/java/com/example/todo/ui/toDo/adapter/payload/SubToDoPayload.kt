package com.example.todo.ui.toDo.adapter.payload

import com.example.todo.ui.toDo.SubToDo


abstract class SubToDoPayload() {
    var id: String? = null
    constructor(id: String) : this() { this.id = id }
    class SubToDoUpdated(id: String,val subToDo: SubToDo): SubToDoPayload(id)
}
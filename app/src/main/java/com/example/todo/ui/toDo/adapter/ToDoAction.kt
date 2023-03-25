package com.example.todo.ui.toDo.adapter

import com.example.todo.ui.toDo.SubToDo
import com.example.todo.ui.toDo.ToDo
import kotlin.jvm.internal.Intrinsics

abstract class ToDoAction {


    class AddSubToDo(private val toDo: ToDo) : ToDoAction() {
        fun getToDo(): ToDo {
            return toDo
        }
    }
    class DeleteToDo(private val toDo: ToDo) : ToDoAction() {
        fun getToDo(): ToDo {
            return toDo
        }
    }
    class DeleteSubToDo(private val toDo: SubToDo) : ToDoAction() {
        fun getSubToDo(): SubToDo {
            return toDo
        }
    }
    class EditToDo(private val toDo: ToDo) : ToDoAction() {
        fun getToDo(): ToDo {
            return toDo
        }
    }
    class CompleteToDo(private val toDo: ToDo) : ToDoAction() {
        fun getToDo(): ToDo {
            return toDo
        }
    }
    class CompleteSubToDo(private val toDo: SubToDo) : ToDoAction() {
        fun getSubToDo(): SubToDo {
            return toDo
        }
    }
    class EditSubToDo(private val toDo: SubToDo) : ToDoAction() {
        fun getSubToDo(): SubToDo {
            return toDo
        }
    }

    class ViewSubToDos(private val toDo: ToDo) : ToDoAction() {
        fun getToDo(): ToDo {
            return toDo
        }
    }
}
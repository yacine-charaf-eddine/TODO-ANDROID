package com.example.todo.ui.toDo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ToDo: ToDoItem() {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("userId")
    @Expose
    var userId: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("completed")
    @Expose
    var completed: Boolean? = null

    @SerializedName("endDate")
    @Expose
    var endDate: String? = null

    @SerializedName("pos")
    @Expose
    var pos: Int? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

}
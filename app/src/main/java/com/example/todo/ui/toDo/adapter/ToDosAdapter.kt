package com.example.todo.ui.toDo.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.todo.R
import com.example.todo.commons.recycleradapter.BaseRecyclerAdapter
import com.example.todo.commons.recycleradapter.BaseViewHolder
import com.example.todo.databinding.SubToDoItemBinding
import com.example.todo.databinding.ToDoItemBinding
import com.example.todo.ui.toDo.SubToDo
import com.example.todo.ui.toDo.ToDo
import com.example.todo.ui.toDo.ToDoItem
import com.example.todo.ui.toDo.adapter.payload.SubToDoPayload
import com.example.todo.ui.toDo.adapter.payload.ToDoPayload
import java.util.*
import kotlin.jvm.internal.Intrinsics

class ToDosAdapter(private val context: Context, private val toDoActionCallback: Function1<ToDoAction, Unit>) : BaseRecyclerAdapter<ToDoItem, BaseViewHolder<ToDoItem>>(context) {
    private val TYPE_TODO = 1
    private val TYPE_SUB_TODO = 2

    override fun createItemViewHolder(layoutInflater: LayoutInflater, viewGroup: ViewGroup, i: Int): BaseViewHolder<ToDoItem> {
        return when (i) {
            TYPE_TODO -> {
                ToDoViewHolder(this, ToDoItemBinding.inflate(layoutInflater, viewGroup, false))
            }
            TYPE_SUB_TODO -> {
                SubToDoViewHolder(this, SubToDoItemBinding.inflate(layoutInflater, viewGroup, false))
            }
            else -> {
                throw RuntimeException("Unknown view-type $i")
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ToDoItem>, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }
        for (payload in payloads) {
            if (payload is ToDoPayload && holder is ToDoViewHolder) {
                holder.applyPayload(payload)
            }
            if (payload is SubToDoPayload && holder is SubToDoViewHolder) {
                holder.applyPayload(payload)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val itemAt: ToDoItem? = getItemAt(position)
        if (itemAt is ToDo) {
            return TYPE_TODO
        }
        return if (itemAt is SubToDo) {
            TYPE_SUB_TODO
        } else super.getItemViewType(position)
    }

    inner class ToDoViewHolder(private val toDoAdapter: ToDosAdapter,private val toDoItemBinding: ToDoItemBinding) : BaseViewHolder<ToDoItem>(toDoItemBinding.rootView) {
        private lateinit var toDo: ToDo
        init {
            toDoItemBinding.completedView.setOnClickListener {
                toDoAdapter.toDoActionCallback.invoke(ToDoAction.CompleteToDo(this.toDo))
            }
            toDoItemBinding.deleteView.setOnClickListener {
                toDoAdapter.toDoActionCallback.invoke(ToDoAction.DeleteToDo(this.toDo))
            }
            toDoItemBinding.editView.setOnClickListener {
                toDoAdapter.toDoActionCallback.invoke(ToDoAction.EditToDo(this.toDo))
            }
            toDoItemBinding.subToDoTriggerStateTextView.setOnClickListener {
                toDoAdapter.toDoActionCallback.invoke(ToDoAction.ViewSubToDos(this.toDo))
            }
            toDoItemBinding.addSubToDoView.setOnClickListener {
                toDoAdapter.toDoActionCallback.invoke(ToDoAction.AddSubToDo(this.toDo))
            }
        }

        override fun setItem(item: ToDoItem) {
            this.toDo = item as ToDo
            this.toDoItemBinding.titleView.text = this.toDo.title
            this.toDoItemBinding.descView.text = this.toDo.description
            this.toDoItemBinding.endDateView.text = this.toDo.endDate
            if(toDo.completed == true){
                toDoItemBinding.completedView.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.green))
            }else{
                toDoItemBinding.completedView.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.steel_gray_300))
            }
        }

        fun applyPayload(payload: ToDoPayload) {
            val toDoId = payload.id
            if (Intrinsics.areEqual(toDoId, this.toDo.id)) {
                if (payload is ToDoPayload.ToDoUpdated){
                    this.toDo.title = payload.toDo.title
                    this.toDo.description = payload.toDo.description
                    this.toDo.completed = payload.toDo.completed
                    this.toDo.endDate = payload.toDo.endDate

                    this.toDoItemBinding.titleView.text = this.toDo.title
                    this.toDoItemBinding.descView.text = this.toDo.description
                    this.toDoItemBinding.endDateView.text = this.toDo.endDate
                    if(toDo.completed == true){
                        toDoItemBinding.completedView.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.green))
                    }else{
                        toDoItemBinding.completedView.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.steel_gray_300))
                    }
                }
            }
        }
    }

    inner class SubToDoViewHolder(private val toDoAdapter: ToDosAdapter,private val subToDoItemBinding: SubToDoItemBinding) : BaseViewHolder<ToDoItem>(subToDoItemBinding.rootView) {
        private lateinit var subToDo: SubToDo
        init {
            subToDoItemBinding.completedView.setOnClickListener {
                toDoAdapter.toDoActionCallback.invoke(ToDoAction.CompleteSubToDo(this.subToDo))
            }
            subToDoItemBinding.deleteView.setOnClickListener {
                toDoAdapter.toDoActionCallback.invoke(ToDoAction.DeleteSubToDo(this.subToDo))
            }
            subToDoItemBinding.editView.setOnClickListener {
                toDoAdapter.toDoActionCallback.invoke(ToDoAction.EditSubToDo(this.subToDo))
            }
        }
        override fun setItem(item: ToDoItem) {
            this.subToDo = item as SubToDo
            this.subToDoItemBinding.titleView.text = this.subToDo.title
            this.subToDoItemBinding.descView.text = this.subToDo.description
            this.subToDoItemBinding.endDateView.text = this.subToDo.endDate
            if(subToDo.completed == true){
                subToDoItemBinding.completedView.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.green))
            }else{
                subToDoItemBinding.completedView.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.steel_gray_300))
            }
        }

        fun applyPayload(payload: SubToDoPayload) {
            val subToDoId = payload.id
            if (Intrinsics.areEqual(subToDoId, this.subToDo.id)) {
                if (payload is SubToDoPayload.SubToDoUpdated){
                    this.subToDo.title = payload.subToDo.title
                    this.subToDo.description = payload.subToDo.description
                    this.subToDo.completed = payload.subToDo.completed
                    this.subToDo.endDate = payload.subToDo.endDate

                    this.subToDoItemBinding.titleView.text = this.subToDo.title
                    this.subToDoItemBinding.descView.text = this.subToDo.description
                    this.subToDoItemBinding.endDateView.text = this.subToDo.endDate
                    if(subToDo.completed == true){
                        subToDoItemBinding.completedView.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.green))
                    }else{
                        subToDoItemBinding.completedView.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.steel_gray_300))
                    }
                }
            }
        }

    }

    private fun findToDoPositionByToDoId(id: String?): Int {
        for ((i, item) in getItems().withIndex()) {
            if (item is ToDo && Intrinsics.areEqual(item.id, id)) {
                return i
            }
        }
        return -1
    }

    private fun findSubToDoPositionBySubToDoId(id: String?): Int {
        for ((i, item) in getItems().withIndex()) {
            if (item is SubToDo && Intrinsics.areEqual(item.id, id)) {
                return i
            }
        }
        return -1
    }

    fun notifyToDoDeleted(todo: ToDo) {
        removeSubTodos(todo.id)
        val position: Int = findToDoPositionByToDoId(todo.id)
        if (position != -1) {
            removeItemAt(position)
        }
    }

    private fun removeSubTodos(id: String?) {
        val hashSet = HashSet<Int>()
        for (i in 0 until getItems().size) {
            if (getItems()[i] is SubToDo && Intrinsics.areEqual((getItems()[i] as SubToDo).todoId, id)) {
                hashSet.add(i)
            }
        }
        if (hashSet.isNotEmpty()) {
            removeItemsAt(hashSet)
        }
    }

    fun notifySubToDoAdded(subToDo: SubToDo): Int? {
        val id: String? = subToDo.todoId
        val position: Int = findToDoPositionByToDoId(id)
        if (position == -1) {
            return null
        }
        val items: List<ToDoItem> = getItems()
        for (i in items.indices) {
            if (items[i] is SubToDo && Intrinsics.areEqual((items[i] as SubToDo).id, id)) {
                val index = position + i + 1
                addItemAt(index, subToDo)
                return index
            }
        }
        val index = position + 1
        addItemAt(index, subToDo)
        return index
    }

    fun notifySubToDoDeleted(subToDo: SubToDo) {
        val position: Int = findSubToDoPositionBySubToDoId(subToDo.id)
        if (position != -1) {
            removeItemAt(position)
        }
    }

    fun showSubToDos(id: String, subToDos: List<SubToDo>) {
        if (subToDos.isEmpty()) {
            return
        }
        var position = 0
        for (toDoItem in getItems()) {
            if (toDoItem is ToDo && Intrinsics.areEqual((toDoItem).id, id)) {
                for (reply in subToDos) {
                    for (commentItem in getItems()) {
                        if (commentItem is SubToDo && Intrinsics.areEqual(commentItem.todoId, id) && Intrinsics.areEqual(commentItem.id, reply.id)) {
                            removeItemAt(getItems().indexOf(commentItem))
                        }
                    }
                }
                position = getItems().indexOf(toDoItem)
            }
        }
        addItemsAt(position + 1, subToDos)
    }


    fun applyPayload(payload: ToDoPayload) {
        val position: Int = findToDoPositionByToDoId(payload.id)
        if (position != -1) {
            notifyItemChanged(position, payload)
        }
    }

    fun applyPayload(payload: SubToDoPayload) {
        val position: Int = findSubToDoPositionBySubToDoId(payload.id)
        if (position != -1) {
            notifyItemChanged(position, payload)
        }
    }
}
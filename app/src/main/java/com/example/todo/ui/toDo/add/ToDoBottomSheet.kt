package com.example.todo.ui.toDo.add

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.DatePicker
import com.example.todo.commons.bottomsheet.BaseBottomSheetDialogFragment
import com.example.todo.databinding.AddToDoBottomSheetBinding
import com.example.todo.ui.home.HomeActivityHandle
import com.example.todo.ui.toDo.SubToDo
import com.example.todo.ui.toDo.ToDo
import com.example.todo.ui.toDo.ToDoItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.internal.Reflection

class ToDoBottomSheet() : BaseBottomSheetDialogFragment<AddToDoBottomSheetBinding>(),
    DatePickerDialog.OnDateSetListener {
    private var toDoItem: ToDoItem? = null
    private var type: Int? = null

    constructor(toDoItem: ToDoItem, type: Int) : this() {
      this.toDoItem = toDoItem
        this.type = type
    }


    companion object {
        val TAG = Reflection.getOrCreateKotlinClass(ToDoBottomSheet::class.java).simpleName
        val TODO_EDIT = 1
        val SUB_TODO_Edit = 2
        val SUB_TODO_Add = 2
    }

    private var activityHandle: HomeActivityHandle? = null

    override fun createBinding(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?
    ): AddToDoBottomSheetBinding {
        return AddToDoBottomSheetBinding.inflate(layoutInflater, viewGroup, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityHandle = context as HomeActivityHandle
    }

    override fun onBindingComplete(bundle: Bundle?) {
        if (toDoItem != null){
            if (toDoItem is ToDo && type != SUB_TODO_Add){
                getBinding().titleView.setText((toDoItem as ToDo).title)
                getBinding().descView.setText((toDoItem as ToDo).description)
                getBinding().endDateView.text = (toDoItem as ToDo).endDate
            }else if (toDoItem is SubToDo){
                getBinding().titleView.setText((toDoItem as SubToDo).title)
                getBinding().descView.setText((toDoItem as SubToDo).description)
                getBinding().endDateView.text = (toDoItem as SubToDo).endDate
            }
        }

        getBinding().endDateView.setOnClickListener {
            context?.let { it ->
                DatePickerDialog(
                    it,
                    this,
                    Calendar.getInstance().get(1),
                    Calendar.getInstance().get(2),
                    Calendar.getInstance().get(5)
                ).show()
            }
        }
        getBinding().submitView.setOnClickListener {
            if (toDoItem != null){
                if (toDoItem is ToDo){
                    if (type == SUB_TODO_Add){
                        val subToDo = SubToDo()
                        subToDo.todoId = (toDoItem as ToDo).id
                        subToDo.title = getBinding().titleView.text.toString()
                        subToDo.description = getBinding().descView.text.toString()
                        subToDo.endDate = getBinding().endDateView.text.toString()
                        subToDo.completed = false
                        activityHandle?.onAddSubToDoRequested(subToDo)
                    }else{
                        val toDo = ToDo()
                        toDo.id = (toDoItem as ToDo).id
                        toDo.title = getBinding().titleView.text.toString()
                        toDo.description = getBinding().descView.text.toString()
                        toDo.endDate = getBinding().endDateView.text.toString()
                        activityHandle?.onUpdateToDoRequested(toDo)
                    }
                }else if (toDoItem is SubToDo){
                    val subToDo = SubToDo()
                    subToDo.id = (toDoItem as SubToDo).id
                    subToDo.title = getBinding().titleView.text.toString()
                    subToDo.description = getBinding().descView.text.toString()
                    subToDo.endDate = getBinding().endDateView.text.toString()
                    activityHandle?.onUpdateSubToDoRequested(subToDo)
                }
            }else{
                val toDo = ToDo()
                toDo.title = getBinding().titleView.text.toString()
                toDo.description = getBinding().descView.text.toString()
                toDo.endDate = getBinding().endDateView.text.toString()
                toDo.completed = false
                activityHandle?.onAddToDoRequested(toDo)
            }
            dismiss()

        }
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val format =
            SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(Calendar.getInstance().time)
        getBinding().endDateView.text = format
    }
}
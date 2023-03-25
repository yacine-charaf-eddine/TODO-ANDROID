package com.example.todo.commons.recycleradapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<ITEM>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun setItem(item: ITEM)
    open fun updateWithPayload(list: List<Any>) {
        checkNotNull(list)
    }
}
package com.example.todo.commons.recycleradapter

class AdapterUpdate<ITEM>(var items: List<ITEM>?, var page: Int) {

    fun isEmpty(): Boolean {
        val list: List<ITEM> = items ?: return true
        return list.isEmpty()
    }

    fun count(): Int {
        val list: List<ITEM> = items ?: return 0
        return list.size
    }
}
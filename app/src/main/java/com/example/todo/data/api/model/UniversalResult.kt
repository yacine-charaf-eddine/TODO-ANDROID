package com.example.todo.data.api.model

import kotlin.jvm.internal.Intrinsics

class UniversalResult<ITEM>(private var code: Int,
                            var message: String, private var item: ITEM?, private var items: List<ITEM>?) {
    private var manualError = false

    fun getCode(): Int {
        return this.code
    }

    fun setCode(i: Int) {
        this.code = i
    }

    fun getItem(): ITEM? {
        return item
    }

    fun setItem(item: ITEM) {
        this.item = item
    }

    fun getItems(): List<ITEM>? {
        return items
    }

    fun setItems(list: List<ITEM>?) {
        items = list
    }

    fun getManualError(): Boolean {
        return manualError
    }

    fun setManualError(z: Boolean) {
        manualError = z
    }

    fun isSuccess(): Boolean {
        val i = this.code
        return i in 200..299
    }

    fun isError(): Boolean {
        val i = this.code
        return i < 200 || i >= 300 || manualError
    }

    fun isSessionExpired(): Boolean {
        return this.code == 401
    }

    fun setManualError() {
        manualError = true
    }

    fun hasItem(): Boolean {
        return item != null
    }

    fun hasNoItem(): Boolean {
        return !hasItem()
    }

    fun hasItems(): Boolean {
        val list = items ?: return false
        return list.isNotEmpty()
    }

    fun hasNoItems(): Boolean {
        return !hasItems()
    }

    fun requireItem(): ITEM {
        val item = item
        if (item != null) {
            return item
        }
        throw IllegalArgumentException("Required value was null.")
    }

    fun requireItems(): List<ITEM> {
        val list = items
        if (list != null) {
            return list
        }
        throw IllegalArgumentException("Required value was null.")
    }
}
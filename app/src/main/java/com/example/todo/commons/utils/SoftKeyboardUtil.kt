package com.example.todo.commons.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import kotlin.jvm.internal.Intrinsics

class SoftKeyboardUtil {
    companion object{
        val INSTANCE = SoftKeyboardUtil()
    }

    private fun getInputMethodManager(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    fun showKeyboard(editText: EditText) {
        Intrinsics.checkNotNullParameter(editText, "editText")
        editText.postDelayed({
            editText.requestFocus()
            val context = editText.context
            val inputMethodManager = INSTANCE.getInputMethodManager(context)
            inputMethodManager.showSoftInput(editText, 1)
        }, 100)
    }

    fun hideKeyboard(editText: EditText) {
        val context = editText.context
        val inputMethodManager = getInputMethodManager(context)
        inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    fun hideKeyboard(activity: Activity) {
        val inputMethodManager = getInputMethodManager(activity)
        val currentFocus = activity.currentFocus
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}
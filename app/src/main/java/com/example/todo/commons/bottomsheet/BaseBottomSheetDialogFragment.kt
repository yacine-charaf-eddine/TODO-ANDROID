package com.example.todo.commons.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.todo.commons.fragment.FragmentRegistrar
import com.example.todo.commons.viewmodel.ViewModelRegistrar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<BINDING: ViewBinding>: BottomSheetDialogFragment() {
    private var _binding: BINDING? = null
    private var fragmentRegistrar: FragmentRegistrar? = null
    private var viewModelRegistrar: ViewModelRegistrar? = null

    abstract fun createBinding(layoutInflater: LayoutInflater, viewGroup: ViewGroup?): BINDING

    open fun onBindingComplete(bundle: Bundle?) {}

    fun getBinding(): BINDING {
        val binding = _binding
        if (binding != null) {
            return binding
        }
        throw IllegalArgumentException("Required value was null.")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentRegistrar = if (context is FragmentRegistrar) context else null
        if (context is ViewModelRegistrar) {
            viewModelRegistrar = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onBindingComplete(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = createBinding(layoutInflater, container)
        return getBinding().root
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}

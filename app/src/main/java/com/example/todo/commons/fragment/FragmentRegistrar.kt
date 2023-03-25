package com.example.todo.commons.fragment

import androidx.fragment.app.Fragment

interface FragmentRegistrar {
    fun getFragmentContainerId(): Int
    fun popFragment()
    fun replaceFragment(fragment: Fragment?, tag: String?, z: Boolean)
}
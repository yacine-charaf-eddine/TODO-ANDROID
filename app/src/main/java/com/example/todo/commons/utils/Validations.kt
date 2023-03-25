package com.example.todo.commons.utils

import java.util.*
import kotlin.jvm.internal.Intrinsics

class Validations {
    companion object{
        val INSTANCE = Validations()
    }

    fun forEmail(email: String?): Int {
        if (email == null || email.isBlank()) {
            return 0
        }
        val regexUtil = RegexUtil.INSTANCE
        val locale = Locale.ROOT
        val lowerCase = email.lowercase(locale)
        return if (!regexUtil.matches(lowerCase, RegexUtil.PATTERN_EMAIL)) 1 else 2
    }
}
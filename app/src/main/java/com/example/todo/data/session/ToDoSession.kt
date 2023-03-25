package com.example.todo.data.session

class ToDoSession {
    companion object{
        val INSTANCE: ToDoSession = ToDoSession()
    }
    private var userId: String? = null
    private var email: String? = null

    fun getUserId(): String? {
        return userId
    }

    fun setUserId(id: String?) {
        this.userId = id
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }
    fun isEmpty(): Boolean {
        if (!(userId == null || userId!!.isBlank())){
            if (!(email == null || email!!.isBlank())){
                return false
            }
        }
        return true
    }

    fun clear() {
        userId = null
        email = null
    }
}
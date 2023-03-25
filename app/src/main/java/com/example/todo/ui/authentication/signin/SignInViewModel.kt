package com.example.todo.ui.authentication.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todo.commons.viewmodel.BaseViewModel
import com.example.todo.data.api.model.AuthResult
import com.example.todo.data.api.model.UniversalResult
import com.example.todo.data.api.model.request.SignInRequest
import com.example.todo.data.source.ToDoDataSource
import com.example.todo.data.storage.StorageRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.internal.Intrinsics

class SignInViewModel(private var toDoDataSource: ToDoDataSource, private var storageRepository: StorageRepository) : BaseViewModel() {
    private val _signInResult = MutableLiveData<String>()
    private var email: String? = null

    private val  signInExceptionHandler : CoroutineExceptionHandler = CoroutineExceptionHandler{ _: CoroutineContext, throwable: Throwable ->
        run {
            var message = throwable.message
            message = if ((throwable is UnknownHostException) || (throwable is SocketTimeoutException)) {
                "Internet is not available."
            } else {
                "Something went wrong."
            }
            toast(Intrinsics.stringPlus("Sign In Error: ", message))
        }
    }

    fun onSignInSuccessful(): LiveData<String> {
        return _signInResult
    }

    fun emailChanged(email: String?) {
        this.email = email
    }

    fun signIn() {
        if (email == null || email!!.isBlank()) {
            toast("Please enter an email or a username")
            return
        }
        signIn(email!!)
    }

    private fun signIn (email: String) {
        viewModelScope.launch(signInExceptionHandler){
            val signInRequest = SignInRequest(email)
            val result : UniversalResult<AuthResult> = toDoDataSource.signIn(signInRequest)
            if(result.isError() || result.hasNoItem()){
                toast(result.message)
                return@launch
            }
            val authResult : AuthResult = result.requireItem()
            storageRepository.userId(authResult.getId())
            storageRepository.email(authResult.getEmail())
            _signInResult.value = result.message
        }
    }
}
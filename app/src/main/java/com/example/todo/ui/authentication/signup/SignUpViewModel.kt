package com.example.todo.ui.authentication.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todo.commons.utils.Validations
import com.example.todo.commons.viewmodel.BaseViewModel
import com.example.todo.data.api.model.AuthResult
import com.example.todo.data.api.model.UniversalResult
import com.example.todo.data.api.model.request.SignInRequest
import com.example.todo.data.api.model.request.SignUpRequest
import com.example.todo.data.source.ToDoDataSource
import com.example.todo.data.storage.StorageRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.internal.Intrinsics

class SignUpViewModel(private var toDoDataSource: ToDoDataSource, private var storageRepository: StorageRepository) : BaseViewModel() {
    private val _signUpResult = MutableLiveData<String>()
    private var email: String? = null

    private val  signUpExceptionHandler : CoroutineExceptionHandler = CoroutineExceptionHandler{ _: CoroutineContext, throwable: Throwable ->
        run {
            var message = throwable.message
            message = if ((throwable is UnknownHostException) || (throwable is SocketTimeoutException)) {
                "Internet is not available."
            } else {
                "Something went wrong."
            }
            toast(Intrinsics.stringPlus("Sign Up Error: ", message))
        }
    }


    fun onSignUpSuccessful(): LiveData<String> {
        return _signUpResult
    }


    fun emailChanged(email: String?) {
        this.email = email
    }

    fun performIdentityValidation() {
        viewModelScope.launch(signUpExceptionHandler){
            if(!isEmailValid(email)){
                return@launch
            }
            completeSignUp()
        }
    }

    private suspend fun isEmailValid(email: String?): Boolean {
        return withContext(Dispatchers.IO){
            val validation = Validations.INSTANCE.forEmail(email)
            if (validation != 2) {
                when (validation) {
                    0 -> {
                        toast("Please enter email")
                    }
                    1 -> {
                        toast("Please enter valid email")
                    }
                    else -> {
                        toast("Something went wrong.")
                    }
                }
                return@withContext false
            }else if (email != null) {
                val result: UniversalResult<AuthResult> = toDoDataSource.fetchEmailsList()
                val emailsList = result.requireItems()
                if (emailsList.isNotEmpty()) {
                    emailsList.forEach {
                        if (it.getEmail() == email){
                            toast("email already exists!")
                            return@withContext false
                        }
                    }
                }
            }
            return@withContext false
        }
    }

    private fun completeSignUp() {
        if (email != null) {
            viewModelScope.launch(signUpExceptionHandler){
                val signUpRequest = SignUpRequest(email!!)
                val result : UniversalResult<AuthResult> = toDoDataSource.signUp(signUpRequest)
                if(result.isError() || result.hasNoItem()){
                    toast("Error: " + result.message)
                    return@launch
                }
                toast(result.message)
                val authResult : AuthResult = result.requireItem()
                storageRepository.userId(authResult.getId())
                storageRepository.email(authResult.getEmail())
                _signUpResult.value = result.message
            }
        } else {
            throw IllegalArgumentException("Required value was null.")
        }
    }
}
package com.example.todo.ui.authentication.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo.commons.utils.SoftKeyboardUtil
import com.example.todo.commons.viewmodel.BaseViewModel
import com.example.todo.commons.viewmodel.BaseViewModelFactory
import com.example.todo.commons.viewmodel.ViewModelRegistrar
import com.example.todo.data.api.ToDoMapper
import com.example.todo.data.domain.ApiServiceGenerator
import com.example.todo.data.source.RemoteTodoDataSource
import com.example.todo.data.source.ToDoRepository
import com.example.todo.data.storage.SharedPrefStorageDataSource
import com.example.todo.data.storage.StorageRepository
import com.example.todo.databinding.SignUpActivityBinding
import com.example.todo.ui.authentication.signin.SignInActivity
import com.example.todo.ui.home.HomeActivity

class SignUpActivity : AppCompatActivity(), ViewModelRegistrar {

    companion object{
        private const val EXTRA_STARTED_BY_SIGN_IN = "intent_extra_started_by_sign_in"
        fun getStartIntent(context: Context, isStartedBy: Boolean): Intent {
            val intent = Intent(context, SignUpActivity::class.java)
            intent.putExtra(EXTRA_STARTED_BY_SIGN_IN, isStartedBy)
            return intent
        }
    }

    private val binding: SignUpActivityBinding by lazy {
        return@lazy SignUpActivityBinding.inflate(layoutInflater, null, false)
    }

    private val signUpViewModel: SignUpViewModel by lazy {
        return@lazy ViewModelProvider(this, BaseViewModelFactory{
            SignUpViewModel(
                ToDoRepository(RemoteTodoDataSource(ApiServiceGenerator.INSTANCE.getToDoClient(), ToDoMapper())), StorageRepository(
                    SharedPrefStorageDataSource(this)
                )
            )
        })[SignUpViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.rootView)
        registerViewModels(signUpViewModel)
        attachObservers()
        attachListeners()
        SoftKeyboardUtil.INSTANCE.showKeyboard(binding.emailView)
    }

    private fun attachListeners() {
        binding.backView.setOnClickListener {
            SoftKeyboardUtil.INSTANCE.hideKeyboard(this)
            onBackPressed()
        }
        binding.emailView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                signUpViewModel.emailChanged(editable.toString().trim())
            }
        })

        binding.signUpButtonView.setOnClickListener {
            SoftKeyboardUtil.INSTANCE.hideKeyboard(this)
            signUpViewModel.performIdentityValidation()
        }

        binding.signInText.setOnClickListener {
            var startedBySignIn = false
            if (intent != null){
                startedBySignIn = intent.getBooleanExtra(EXTRA_STARTED_BY_SIGN_IN, false)
            }
            if (startedBySignIn){
                finish()
            }else{
                startActivity(SignInActivity.getStartIntent(this, true))
            }
        }
    }

    private fun attachObservers() {
        signUpViewModel.onSignUpSuccessful().observe(this) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }


    override fun registerViewModels(vararg viewModelArr: ViewModel) {
        for (viewModel in viewModelArr) {
            registerViewModel(viewModel)
        }
    }

    private fun registerViewModel(viewModel: ViewModel) {
        if (viewModel is LifecycleObserver) {
            lifecycle.addObserver(viewModel as LifecycleObserver)
        }
        if (viewModel is BaseViewModel) {
            registerViewModel(viewModel)
        }
    }
    private fun registerViewModel(baseViewModel: BaseViewModel) {
        baseViewModel.getToastLiveData().observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
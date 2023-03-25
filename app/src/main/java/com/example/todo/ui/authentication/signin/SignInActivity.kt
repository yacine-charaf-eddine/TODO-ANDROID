package com.example.todo.ui.authentication.signin

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
import com.example.todo.databinding.SignInActivityBinding
import com.example.todo.ui.authentication.signup.SignUpActivity
import com.example.todo.ui.home.HomeActivity

class SignInActivity : AppCompatActivity(), ViewModelRegistrar {

    companion object{
        private const val EXTRA_STARTED_BY_SIGN_UP = "intent_extra_started_by_sign_up"
        fun getStartIntent(context: Context, z: Boolean): Intent {
            val intent = Intent(context, SignInActivity::class.java)
            intent.putExtra(EXTRA_STARTED_BY_SIGN_UP, z)
            return intent
        }
    }

    private val binding: SignInActivityBinding by lazy {
        return@lazy SignInActivityBinding.inflate(layoutInflater, null, false)
    }

    private val signInViewModel: SignInViewModel by lazy {
        return@lazy ViewModelProvider(this, BaseViewModelFactory{
            SignInViewModel(ToDoRepository(RemoteTodoDataSource(ApiServiceGenerator.INSTANCE.getToDoClient(), ToDoMapper())), StorageRepository(SharedPrefStorageDataSource(this)))
        })[SignInViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.rootView)
        registerViewModels(signInViewModel)
        attachObservers()
        attachListeners()
        SoftKeyboardUtil.INSTANCE.showKeyboard(binding.identityView)
    }

    private fun attachListeners() {
        binding.identityView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                signInViewModel.emailChanged(editable.toString().trim())
            }
        })

        binding.signInButtonView.setOnClickListener {
            signInViewModel.signIn()
        }

        binding.signUpText.setOnClickListener {
            var startedBySignUp = false
            if (intent != null){
                startedBySignUp = intent.getBooleanExtra(EXTRA_STARTED_BY_SIGN_UP, false)
            }
            if (startedBySignUp){
                finish()
            }else{
                startActivity(SignUpActivity.getStartIntent(this, true))
            }
        }
    }

    private fun attachObservers() {
        signInViewModel.onSignInSuccessful().observe(this) {
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
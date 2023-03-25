package com.example.todo.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

import com.example.todo.ui.authentication.signin.SignInActivity
import com.example.todo.ui.home.HomeActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity: AppCompatActivity() {

    private val checkSessionViewModel: CheckSessionViewModel by lazy {
        return@lazy CheckSessionViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkSessionViewModel.checkSession()

        checkSessionViewModel.showHomePageLiveData.observe(this) {
            if (it){
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }
        checkSessionViewModel.showAuthPromptPageLiveData.observe(this) {
            if (it){
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }
        }
    }
}
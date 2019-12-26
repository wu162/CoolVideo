package com.example.coolvideo.ui.login

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.coolvideo.R
import com.example.coolvideo.databinding.ActivityLoginBinding
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity() {

    private val mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityLoginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login)

        loginViewModel= ViewModelProviders.of(this,LoginModelFactory(this)).get(LoginViewModel::class.java)
        binding.loginViewModel=loginViewModel

        loginViewModel.submitClick.observe(this, Observer {
            binding.loginSubmit.isEnabled=false
        })

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onBackPressed() {
        finishAffinity()
        exitProcess(0)
    }
}

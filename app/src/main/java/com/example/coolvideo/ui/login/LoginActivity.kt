package com.example.coolvideo.ui.login

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.coolvideo.R
import com.example.coolvideo.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityLoginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login)

        loginViewModel= ViewModelProviders.of(this,LoginModelFactory(this)).get(LoginViewModel::class.java)
        binding.loginViewModel=loginViewModel

        loginViewModel.submitClick.observe(this, Observer {
            binding.loginSubmit.isEnabled=false
        })
    }
}

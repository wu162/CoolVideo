package com.example.coolvideo.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coolvideo.data.Repository.HomeVideosRepository

class HomeModelFactory(private val context : Context,private val repository: HomeVideosRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(context, repository) as T
    }
}
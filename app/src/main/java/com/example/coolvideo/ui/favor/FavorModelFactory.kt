package com.example.coolvideo.ui.favor

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coolvideo.data.repository.FavorRepository

class FavorModelFactory (private val context: Context, private val repository: FavorRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavorViewModel(context, repository) as T
    }
}
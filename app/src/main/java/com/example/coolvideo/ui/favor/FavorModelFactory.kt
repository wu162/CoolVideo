package com.example.coolvideo.ui.favor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coolvideo.data.Repository.FavorRepository

class FavorModelFactory (private val repository: FavorRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavorViewModel(repository) as T
    }
}
package com.example.coolvideo.ui.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coolvideo.data.Repository.HistoryRepository

class HistoryModelFactory(private val context: Context, private val repository: HistoryRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HistoryViewModel(context, repository) as T
    }
}
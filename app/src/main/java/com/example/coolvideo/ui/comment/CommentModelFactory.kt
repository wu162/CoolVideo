package com.example.coolvideo.ui.comment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coolvideo.data.Repository.CommentRepository

class CommentModelFactory (private val context : Context, private val repository: CommentRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CommentViewModel(context, repository) as T
    }
}
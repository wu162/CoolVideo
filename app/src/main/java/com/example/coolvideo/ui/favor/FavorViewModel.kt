package com.example.coolvideo.ui.favor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolvideo.data.Repository.FavorRepository
import com.example.coolvideo.data.model.Favor
import kotlinx.coroutines.launch

class FavorViewModel (private val repository: FavorRepository) : ViewModel() {
    var dataChanged = MutableLiveData<Int>()

    var favors=ArrayList<Favor>()

    init {
        launch{
            repository.deleteAllFavor()
        }
    }

    fun getFavor(){
        launch{
            val favor=repository.getFavors()
            favors.addAll(favor)
        }
    }

    private fun launch(block: suspend () -> Unit) = viewModelScope.launch {
        try {
            block()
            dataChanged.value = dataChanged.value?.plus(1)
        } catch (t: Throwable) {
            t.printStackTrace()
            dataChanged.value = dataChanged.value?.plus(1)
        }
    }
}
package com.example.coolvideo.ui.favor

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolvideo.data.Repository.FavorRepository
import com.example.coolvideo.data.model.Favor
import kotlinx.coroutines.launch

class FavorViewModel (private val context: Context, private val repository: FavorRepository) : ViewModel() {
    var dataChanged = MutableLiveData<Int>()

    var favors=ArrayList<Favor>()

    init {
        launch{
            repository.deleteAllFavor()
        }
    }

    fun getFavor(){
        launch{
            var pref=context.getSharedPreferences("userInfo", MODE_PRIVATE)
            val id=pref.getString("id","").toString()
            val favor=repository.getFavors(id)
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
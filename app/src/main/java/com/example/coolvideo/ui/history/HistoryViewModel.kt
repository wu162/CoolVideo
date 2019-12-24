package com.example.coolvideo.ui.history

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolvideo.data.Repository.HistoryRepository
import com.example.coolvideo.data.model.History
import com.example.coolvideo.data.model.Video
import kotlinx.coroutines.launch

class HistoryViewModel(private val context: Context, private val repository: HistoryRepository) : ViewModel() {
    var dataChanged = MutableLiveData<Int>()

    var historys=ArrayList<History>()

    init {
        launch{
            repository.deleteAllHistory()
        }
    }

    fun getHistory(){
        launch{
            var pref=context.getSharedPreferences("userInfo", MODE_PRIVATE)
            val id=pref.getString("id","").toString()
            val history=repository.getHistorys(id)
            historys.addAll(history)
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
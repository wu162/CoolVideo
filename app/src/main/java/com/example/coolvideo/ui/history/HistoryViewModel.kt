package com.example.coolvideo.ui.history

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolvideo.data.repository.HistoryRepository
import com.example.coolvideo.data.model.History
import com.example.coolvideo.ui.video.VideoActivity
import com.example.coolvideo.utils.DateUtils
import kotlinx.coroutines.launch

class HistoryViewModel(private val context: Context, private val repository: HistoryRepository) : ViewModel(),HistoryItemListener {
    var dataChanged = MutableLiveData<Int>()
    var isLoading= ObservableField<Boolean>()

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

    override fun onItemClick(history: History) {
        startVideoActivity(history)
    }

    private fun startVideoActivity(history: History) {
        var intent= Intent(context, VideoActivity::class.java)
        intent.putExtra("videoUrl", history.videoUrl)
        intent.putExtra("videoName", history.videoName)
        intent.putExtra("videoId", history.videoId)
        intent.putExtra("videoUrl", history.videoUrl)
        intent.putExtra("videoImgUrl", history.videoImgUrl)
        context.startActivity(intent)
    }

    public fun onRefresh(){
        isLoading.set(true)
        launch {
            repository.deleteAllHistory()
            var pref=context.getSharedPreferences("userInfo", MODE_PRIVATE)
            val id=pref.getString("id","").toString()
            val history=repository.getHistorys(id)
            historys.clear()
            historys.addAll(history)
            isLoading.set(false)
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

    companion object{
        private const val baseUrl="http://47.100.37.242:8080"
        private const val videoPath="/videos/"
    }
}
package com.example.coolvideo.ui.history

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolvideo.data.Repository.HistoryRepository
import com.example.coolvideo.data.model.History
import com.example.coolvideo.data.model.Video
import com.example.coolvideo.ui.Video.VideoActivity
import com.example.coolvideo.utils.DateUtils
import kotlinx.coroutines.launch

class HistoryViewModel(private val context: Context, private val repository: HistoryRepository) : ViewModel(),HistoryItemListener {
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

    override fun onItemClick(history: History) {
        var pref=context.getSharedPreferences("userInfo", MODE_PRIVATE)
        val id=pref.getString("id","").toString()
        launch {
            repository.addHistory(id,history.videoId.toString(),history.videoName,
                history.videoUrl,history.videoImgUrl, DateUtils.nowDateTime)
            startVideoActivity(history.videoUrl)
        }
    }

    private fun startVideoActivity(videoUrl: String) {
        var intent= Intent(context, VideoActivity::class.java)
        intent.putExtra("videoUrl", baseUrl+ videoPath+videoUrl)
        context.startActivity(intent)
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
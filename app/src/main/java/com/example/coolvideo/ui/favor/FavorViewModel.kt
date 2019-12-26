package com.example.coolvideo.ui.favor

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolvideo.data.repository.FavorRepository
import com.example.coolvideo.data.model.Favor
import com.example.coolvideo.ui.video.VideoActivity
import kotlinx.coroutines.launch

class FavorViewModel (private val context: Context, private val repository: FavorRepository) : ViewModel() , FavorItemListener {
    var dataChanged = MutableLiveData<Int>()
    var isLoading= ObservableField<Boolean>()

    var favors=ArrayList<Favor>()

    fun getFavor(){
        launch{
            var pref=context.getSharedPreferences("userInfo", MODE_PRIVATE)
            val id=pref.getString("id","").toString()
            val favor=repository.getFavors(id)
            favors.addAll(favor)
        }
    }

    override fun onItemClick(favor: Favor) {
        startVideoActivity(favor)
    }

    private fun startVideoActivity(favor: Favor) {
        var intent= Intent(context, VideoActivity::class.java)
        intent.putExtra("videoUrl", baseUrl+ videoPath+favor.videoUrl)
        intent.putExtra("videoName", favor.videoName)
        intent.putExtra("videoId", favor.videoId)
        intent.putExtra("videoUrl", favor.videoUrl)
        intent.putExtra("videoImgUrl", favor.videoImgUrl)
        context.startActivity(intent)
    }

    public fun onRefresh(){
        isLoading.set(true)
        launch {
            repository.deleteAllFavor()
            var pref=context.getSharedPreferences("userInfo", MODE_PRIVATE)
            val id=pref.getString("id","").toString()
            val favor=repository.getFavors(id)
            favors.clear()
            favors.addAll(favor)
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
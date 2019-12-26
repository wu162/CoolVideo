package com.example.coolvideo.data.DAO

import com.example.coolvideo.data.model.History
import org.litepal.LitePal
import java.sql.Timestamp

class HistoryDao {
    fun getHistoryList():MutableList<History>{
        return LitePal.findAll(History::class.java)
    }

    fun saveHistoryList(historyList: List<History>?) {
        if (historyList != null && historyList.isNotEmpty()) {
            LitePal.saveAll(historyList)
        }
    }

    fun findHistory(history: History): MutableList<History>? {
        return LitePal.where("userId=? and videoId=?",history.userId,history.videoId).find(History::class.java)
    }

    fun updateHistoryTime(history: History){
        history.updateAll("videoId=?",history.videoId)
    }

    fun insertHistory(history: History){
        history.save()
    }

    fun deteleAllHistory(){
        LitePal.deleteAll(History::class.java)
    }
}
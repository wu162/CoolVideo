package com.example.coolvideo.data.DAO

import com.example.coolvideo.data.model.History
import org.litepal.LitePal

class HistoryDao {
    fun getHistoryList():MutableList<History>{
        return LitePal.findAll(History::class.java)
    }

    fun saveHistoryList(historyList: List<History>?) {
        if (historyList != null && historyList.isNotEmpty()) {
            LitePal.saveAll(historyList)
        }
    }

    fun insertHistory(history: History){
        history.save()
    }

    fun deteleAllHistory(){
        LitePal.deleteAll(History::class.java)
    }
}
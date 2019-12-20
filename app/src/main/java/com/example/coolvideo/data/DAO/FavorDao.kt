package com.example.coolvideo.data.DAO

import com.example.coolvideo.data.model.Favor
import org.litepal.LitePal

class FavorDao {
    fun getFavorList():MutableList<Favor>{
        return LitePal.findAll(Favor::class.java)
    }

    fun saveFavorList(favorList: List<Favor>?) {
        if (favorList != null && favorList.isNotEmpty()) {
            LitePal.saveAll(favorList)
        }
    }

    fun insertFavor(favor: Favor){
        favor.save()
    }

    fun deteleAllFavor(){
        LitePal.deleteAll(Favor::class.java)
    }
}
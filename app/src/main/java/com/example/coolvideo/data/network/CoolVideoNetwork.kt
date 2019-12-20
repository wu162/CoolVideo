package com.example.coolvideo.data.network

import com.example.coolvideo.data.network.api.FavorService
import com.example.coolvideo.data.network.api.HistoryService
import com.example.coolvideo.data.network.api.HomeFragService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CoolVideoNetwork {
    private val homeFragService=ServiceCreator.create(HomeFragService::class.java)
    private val historyService=ServiceCreator.create(HistoryService::class.java)
    private val favorService=ServiceCreator.create(FavorService::class.java)

    suspend fun fetchHomeFragVideos()=homeFragService.getHomeFragVideos().await()
    suspend fun fetchHistory()=historyService.getHistory().await()
    suspend fun fetchFavor()=favorService.getFavor().await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

    companion object{
        private var network: CoolVideoNetwork?=null
        fun getInstance(): CoolVideoNetwork{
            if(network==null){
                synchronized(CoolVideoNetwork::class.java) {
                    if (network == null) {
                        network = CoolVideoNetwork()
                    }
                }
            }
            return network!!
        }
    }
}
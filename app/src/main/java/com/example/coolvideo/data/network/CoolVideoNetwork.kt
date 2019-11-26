package com.example.coolvideo.data.network

import com.example.coolvideo.data.network.api.HomeFragService

class CoolVideoNetwork {
    private val homeFragService=ServiceCreator.create(HomeFragService::class.java)
    suspend fun fetchHomeFragVIdeos()=homeFragService.getHomeFragVideos()

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
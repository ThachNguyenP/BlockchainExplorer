package com.example.blockexplorer

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BlockServiceApi {
    @GET("blocks/{height}")
    suspend fun listBlock(@Path("height") blockHeight: Int?): Response<List<Block>>

    @GET("block/{hash}")
    suspend fun getBlock(@Path("hash") hash: String): Response<BlockDetail>
}
package com.example.blockexplorer

import com.squareup.moshi.Json

data class BlockDetail(
    @Json(name = "id")
    val id: String,
    @Json(name = "height")
    val blockHeight: Int,
    @Json(name = "tx_count")
    val transaction: Int,
    @Json(name = "size")
    val size: Float
)
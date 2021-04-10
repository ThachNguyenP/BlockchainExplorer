package com.example.blockexplorer

import com.squareup.moshi.Json

data class Block(
    @Json(name = "id")
    val id: String,
    @Json(name = "height")
    val height: Int,
    @Json(name = "tx_count")
    val transaction: Int,
    @Json(name = "size")
    val size: Float
)

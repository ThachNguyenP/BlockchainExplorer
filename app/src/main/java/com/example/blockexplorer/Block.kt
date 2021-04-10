package com.example.blockexplorer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
@Entity(tableName = "block")
data class Block(
    @ColumnInfo(name = "id")
    @Json(name = "id")
    val id: String,
    @ColumnInfo(name = "height")
    @PrimaryKey
    @Json(name = "height")
    val height: Int,
    @ColumnInfo(name = "tx_count")
    @Json(name = "tx_count")
    val transaction: Int,
    @ColumnInfo(name = "size")
    @Json(name = "size")
    val size: Float
)

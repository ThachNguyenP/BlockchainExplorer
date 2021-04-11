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
    val size: Float,
    @ColumnInfo(name = "timestamp")
    @Json(name = "timestamp")
    val timestamp: Int,
    @ColumnInfo(name = "weight")
    @Json(name = "weight")
    val weight: Int,
    @ColumnInfo(name = "version")
    @Json(name = "version")
    val version: Int,
    @ColumnInfo(name = "merkle_root")
    @Json(name = "merkle_root")
    val merkleRoot: String,
    @ColumnInfo(name = "bits")
    @Json(name = "bits")
    val bits: String,
    @ColumnInfo(name = "difficulty")
    @Json(name = "difficulty")
    val difficulty: String,
    @ColumnInfo(name = "nonce")
    @Json(name = "nonce")
    val nonce: String
)

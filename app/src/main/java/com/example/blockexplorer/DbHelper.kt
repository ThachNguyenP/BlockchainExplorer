package com.example.blockexplorer

import android.app.Application
import androidx.room.*

object DbHelper {
    private lateinit var db: AppDatabase
    fun init(context: Application) {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "block_db"
        ).build()
    }

    fun getBlockDao() = db.blockDao()

    @Database(entities = [Block::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun blockDao(): BlockDao
    }

}

@Dao
interface BlockDao {
    @Transaction
    @Query("SELECT * FROM block where height <= :blockHeight limit 10")
    suspend fun getBlocks(blockHeight: Int): List<Block>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlocks(list: List<Block>)
}
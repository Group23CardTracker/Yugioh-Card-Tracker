package com.example.yu_gi_ohcardtracker.bannedandNew

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BannedDao {
    @Query("SELECT * FROM banned_table")
    fun getAll(): Flow<List<BannedEntity>>

    @Insert
    fun insertAll(bannedCards: List<BannedEntity>)

    @Query("DELETE FROM banned_table")
    fun deleteAll()
}
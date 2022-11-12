package com.example.yu_gi_ohcardtracker.bannedandNew

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewCardDao {
    @Query("SELECT * FROM new_card_table")
    fun getAll(): Flow<List<NewCardEntity>>

    @Insert
    fun insertAll(newCards: List<NewCardEntity>)

    @Query("DELETE FROM new_card_table")
    fun deleteAll()
}
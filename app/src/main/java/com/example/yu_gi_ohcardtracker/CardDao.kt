package com.example.yu_gi_ohcardtracker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface CardDao {
    @Query("SELECT * FROM home_table")
    fun getAll(): Flow<List<CardEntity>>

    @Insert
    fun insertAll(items: List<CardEntity>)

    @Insert
    fun insert(item: CardEntity)

    @Query("DELETE FROM home_table")
    fun deleteAll()

    @Query ("SELECT COUNT(*) FROM home_table")
    fun getCount(): Int?
}

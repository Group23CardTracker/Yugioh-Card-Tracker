package com.example.yu_gi_ohcardtracker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface CardDao {
    @Query("SELECT * FROM home_table")
    fun getAll(): Flow<List<HomeEntity>>

    @Insert
    fun insertAll(items: List<HomeEntity>)

    @Insert
    fun insert(item: HomeEntity)

    @Query("DELETE FROM home_table")
    fun deleteAll()
}

package com.example.yu_gi_ohcardtracker.collection

import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface CollectionDao {
    @Query("SELECT * FROM collection_table")
    fun getAll(): Flow<List<CollectionEntity>>

    @Insert
    fun insertAll(items: List<CollectionEntity>)

    @Insert
    fun insert(item: CollectionEntity)

    @Query("DELETE FROM collection_table")
    fun deleteAll()
}
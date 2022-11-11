package com.example.yu_gi_ohcardtracker.collection

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {
    @Query("SELECT * FROM collection_table")
    fun getAll(): Flow<List<CollectionEntity>>

    @Insert
    fun insert(item: CollectionEntity)

    @Query("DELETE FROM collection_table")
    fun deleteAll()

    @Query("SELECT COUNT() FROM collection_table WHERE name = :name")
    fun exists(name: String?): Int

    @Query("DELETE FROM collection_table WHERE name = :name")
    fun delete(name: String?)
}
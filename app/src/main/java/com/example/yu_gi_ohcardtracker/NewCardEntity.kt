package com.example.yu_gi_ohcardtracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "new_card_table")
data class NewCardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "setName") val setName: String?,
    @ColumnInfo(name = "setRarity") val setRarity: String?,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?
)
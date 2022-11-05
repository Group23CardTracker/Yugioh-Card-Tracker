package com.example.yu_gi_ohcardtracker.banned

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "banned_table")
data class BannedEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "banStatus") val banStatus: String?,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
    @ColumnInfo(name = "setName") val setName: String?,
    @ColumnInfo(name = "setRarity") val setRarity: String?,
)
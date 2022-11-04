package com.example.yu_gi_ohcardtracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "home_table")
data class HomeEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "img") val img: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)


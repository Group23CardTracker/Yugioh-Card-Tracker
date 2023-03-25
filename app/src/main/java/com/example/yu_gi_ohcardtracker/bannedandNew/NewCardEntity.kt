package com.example.yu_gi_ohcardtracker.bannedandNew

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "new_card_table")
data class NewCardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "img") val img: String?,
    @ColumnInfo(name = "desc") val desc: String?,
    @ColumnInfo(name = "level") val level: Int?,
    @ColumnInfo(name = "atk") val atk: Int?,
    @ColumnInfo(name = "def") val def: Int?,
    @ColumnInfo(name = "cardmarket_price") val cardmarket_price: String,
    @ColumnInfo(name = "tcgPlayerPrice") val tcgPlayerPrice: String,
    @ColumnInfo(name = "ebayPrice") val ebayPrice: String,
    @ColumnInfo(name = "tcgBanStatus") val tcgBanStatus: String?,
    @ColumnInfo(name = "ocgBanStatus") val ocgBanStatus: String?,
    @ColumnInfo(name = "setName") val setName: String?,
    @ColumnInfo(name = "setRarity") val setRarity: String?
)
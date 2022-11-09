package com.example.yu_gi_ohcardtracker

data class DisplayCard(
    val name: String?,
    val imageUrl: String?,
    val smallImg: String?,
    val desc: String?,
    val level: Int?,
    val atk: Int?,
    val def: Int?,
    val cardmarket_price: String?,
    val tcgPlayerPrice: String?,
    val ebayPrice: String,
    val banStatus: String?,
    val setName: String?,
    val setRarity: String?,
) : java.io.Serializable
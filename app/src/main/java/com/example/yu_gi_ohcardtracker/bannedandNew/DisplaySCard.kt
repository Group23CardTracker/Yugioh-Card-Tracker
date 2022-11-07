package com.example.yu_gi_ohcardtracker.bannedandNew

data class DisplaySCard(
    val name: String?,
    val banStatus: String?,
    val imageUrl: String?,
    val setName: String?,
    val setRarity: String?,
) : java.io.Serializable
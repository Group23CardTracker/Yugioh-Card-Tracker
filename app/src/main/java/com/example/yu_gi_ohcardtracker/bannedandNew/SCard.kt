package com.example.yu_gi_ohcardtracker.bannedandNew

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SearchNewData(
    @SerialName("data")
    val data: List<BannedCard>?
)

@Keep
@Serializable
data class BannedCard(
    @SerialName("name")
    val name: String?,
    @SerialName("card_images")
    val images: List<Images>?,
    @SerialName("banlist_info")
    val banlistInfo: BanStatus? = null,
    @SerialName("card_sets")
    val sets: List<Sets>?
) : java.io.Serializable{
    val imageUrl = images?.firstOrNull{it.url != null}?.url ?: ""
    val setName = sets?.firstOrNull{it.set_name != null}?.set_name ?: ""
    val setRarity = sets?.firstOrNull { it.set_rarity != null }?.set_rarity ?: ""
}

@Keep
@Serializable
data class Sets(
    @SerialName("set_name")
    val set_name: String?,
    @SerialName("set_rarity")
    val set_rarity: String?
) : java.io.Serializable

@Keep
@Serializable
data class BanStatus(
    @SerialName("ban_tcg")
    val banStatus: String? = null
) : java.io.Serializable

@Keep
@Serializable
data class Images(
    @SerialName("image_url")
    val url: String?
) : java.io.Serializable
package com.example.yu_gi_ohcardtracker

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
    val banlistInfo: BanStatus?
) : java.io.Serializable{
    val imageUrl = images?.firstOrNull{it.url != null}?.url ?: ""
}

@Keep
@Serializable
data class BanStatus(
    @SerialName("ban_tcg")
    val banStatus: String?
) : java.io.Serializable

@Keep
@Serializable
data class Images(
    @SerialName("image_url")
    val url: String?
) : java.io.Serializable
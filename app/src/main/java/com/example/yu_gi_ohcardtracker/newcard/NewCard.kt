package com.example.yu_gi_ohcardtracker.newcard

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SearchNewCardData(
    @SerialName("data")
    val data: List<NewCard>?
)

@Keep
@Serializable
data class NewCard(
    @SerialName("name")
    val name: String?,
    @SerialName("card_images")
    val images: List<NewImages>?,
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
data class NewImages(
    @SerialName("image_url")
    val url: String?
) : java.io.Serializable
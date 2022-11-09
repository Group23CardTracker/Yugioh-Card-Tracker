package com.example.yu_gi_ohcardtracker

import android.support.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SearchData(
    @SerialName("data")
    val data: List<Card>?
)

@Keep
@Serializable
data class Card (
    @SerialName("name")
    val name: String? = null,
    @SerialName("card_images")
    val img: List<CardImages>?,
    @SerialName("desc")
    val desc: String? = null,
    @SerialName("level")
    val level: Int? = null,
    @SerialName("atk")
    val atk: Int? = null,
    @SerialName("def")
    val def: Int? = null,
    @SerialName("card_prices")
    val prices: List<CardPrices>? = null,
    @SerialName("banlist_info")
    val banlistInfo: BanStatus? = null,
    @SerialName("card_sets")
    val sets: List<Sets>? = null
) : java.io.Serializable{
    val imageUrl = img?.firstOrNull{it.url != null}?.url ?: ""
    val setName = sets?.firstOrNull{it.set_name != null}?.set_name ?: ""
    val setRarity = sets?.firstOrNull { it.set_rarity != null }?.set_rarity ?: ""
    val cardmarketPrice = prices?.firstOrNull{it.cardmarket_price != null}?.cardmarket_price ?: ""
    val tcgplayerPrice = prices?.firstOrNull{it.tcgPlayerPrice != null}?.tcgPlayerPrice ?: ""
    val ebay = prices?.firstOrNull{it.ebayPrice != null}?.ebayPrice ?: ""
    val smallImg = img?.firstOrNull{it.imageUrlSmall != null}?.imageUrlSmall ?: ""
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
data class CardPrices (
    @SerialName("cardmarket_price")
    val cardmarket_price: String? = null,
    @SerialName("tcgplayer_price")
    val tcgPlayerPrice: String? = null,
    @SerialName("ebay_price")
    val ebayPrice: String? = null,
): java.io.Serializable

@Keep
@Serializable
data class CardImages (
    @SerialName("image_url")
    val url: String?,
    @SerialName("image_url_small")
    val imageUrlSmall: String? = null,
): java.io.Serializable

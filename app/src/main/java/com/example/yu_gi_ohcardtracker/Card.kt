package com.example.yu_gi_ohcardtracker

import android.support.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Card (
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("image_url")
    val img: String? = null,
    @SerializedName("desc")
    val desc: String? = null,
    @SerializedName("level")
    val level: Int? = null,
    @SerializedName("atk")
    val atk: Int? = null,
    @SerializedName("def")
    val def: Int? = null,
    @SerializedName("card_prices")
    val prices: List<CardPrices>? = null,
    @SerializedName("card_images")
    val images: List<CardImages>? = null,
) : java.io.Serializable

@Keep
@Serializable
data class CardPrices (
    @SerializedName("cardmarket_price")
    val cardmarket_price: String? = null,
    @SerializedName("tcgplayer_price")
    val tcgPlayerPrice: String? = null,
    @SerializedName("ebay_price")
    val ebayPrice: String? = null,
): java.io.Serializable

@Keep
@Serializable
data class CardImages (
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("image_url_small")
    val imageUrlSmall: String? = null,
): java.io.Serializable

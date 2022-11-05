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
    @SerializedName("card_images")
    val images: List<CardImages>? = null,
) : java.io.Serializable

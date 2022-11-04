package com.example.yu_gi_ohcardtracker

import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Card (
    @SerializedName("name")
    val name: String?,
//    @SerializedName("card_images")
//    val img: List<>,
) : java.io.Serializable
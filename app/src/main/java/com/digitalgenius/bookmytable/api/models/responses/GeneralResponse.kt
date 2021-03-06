package com.digitalgenius.bookmytable.api.models.responses


import com.google.gson.annotations.SerializedName

data class GeneralResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("success")
    val success: Int,
    @SerializedName("restaurant_id")
    val restaurant_id: String?
)
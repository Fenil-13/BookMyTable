package com.digitalgenius.bookmytable.api.models.responses


import com.google.gson.annotations.SerializedName

data class OrderCompletedResponse(
    @SerializedName("success")
    val success: Int
)
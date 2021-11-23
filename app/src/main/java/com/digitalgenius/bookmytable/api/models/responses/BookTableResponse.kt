package com.digitalgenius.bookmytable.api.models.responses


import com.google.gson.annotations.SerializedName

data class BookTableResponse(
    @SerializedName("booking_id")
    val bookingId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("success")
    val success: Int
)
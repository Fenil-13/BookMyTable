package com.digitalgenius.bookmytable.api.models.requests


import com.google.gson.annotations.SerializedName

data class OrderCompletedRequest(
    @SerializedName("booking_id")
    val bookingId: String
)
package com.digitalgenius.bookmytable.api.models.responses


import com.digitalgenius.bookmytable.api.models.entities.Booking
import com.google.gson.annotations.SerializedName

data class UserBookingHistoryResponse(
    @SerializedName("booking_count")
    val bookingCount: Int,
    @SerializedName("bookingList")
    val bookingList: List<Booking>,
    @SerializedName("success")
    val success: Int
)
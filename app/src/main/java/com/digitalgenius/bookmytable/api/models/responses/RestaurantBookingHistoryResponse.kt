package com.digitalgenius.bookmytable.api.models.responses


import com.google.gson.annotations.SerializedName

data class RestaurantBookingHistoryResponse(
    @SerializedName("completedBookingList")
    val completedBookingList: List<BookingResponse>,
    @SerializedName("incompleteBookingList")
    val incompleteBookingList: List<BookingResponse>,
    @SerializedName("success")
    val success: Int
)
package com.digitalgenius.bookmytable.api.models.responses


import com.google.gson.annotations.SerializedName

data class BookingResponse(
    @SerializedName("booking_date")
    val bookingDate: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("restaurant_id")
    val restaurantId: String,
    @SerializedName("restaurant_name")
    val restaurantName: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("table_id")
    val tableId: String,
    @SerializedName("table_type")
    val tableType: String,
    @SerializedName("time_slot")
    val timeSlot: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_name")
    val userName: String
)
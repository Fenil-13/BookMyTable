package com.digitalgenius.bookmytable.api.models.requests


import com.google.gson.annotations.SerializedName

data class UserBookingRequest(
    @SerializedName("user_id")
    val userId: String
)
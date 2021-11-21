package com.digitalgenius.bookmytable.api.models.requests


import com.google.gson.annotations.SerializedName

data class SignUpUserRequest(
    @SerializedName("user_auth_id")
    val userAuthId: String,
    @SerializedName("user_device_token")
    val userDeviceToken: String,
    @SerializedName("user_email")
    val userEmail: String,
    @SerializedName("user_location")
    val userLocation: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("user_phone_number")
    val userPhoneNumber: String
)
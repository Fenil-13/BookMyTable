package com.digitalgenius.bookmytable.api.models.entities


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_auth_id")
    val userAuthId: String,
    @SerializedName("user_device_token")
    val userDeviceToken: String,
    @SerializedName("user_email")
    val userEmail: String,
    @SerializedName("_id")
    val userId: String,
    @SerializedName("user_location")
    val userLocation: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("user_phone_number")
    val userPhoneNumber: String,
    @SerializedName("user_profile_pic")
    val userProfilePic: String
)
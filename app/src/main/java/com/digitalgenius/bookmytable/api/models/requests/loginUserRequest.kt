package com.digitalgenius.bookmytable.api.models.requests


import com.google.gson.annotations.SerializedName

data class loginUserRequest(
    @SerializedName("user_auth_id")
    val userAuthId: String,
    @SerializedName("user_phone_number")
    val userPhoneNumber: String
)
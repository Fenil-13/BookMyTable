package com.digitalgenius.api.models.responses


import com.digitalgenius.api.models.entities.UserData
import com.google.gson.annotations.SerializedName

data class LoginUserResponse(
    @SerializedName("success")
    val success: Int,
    @SerializedName("user_data")
    val userData: UserData?,
    @SerializedName("user_status")
    val userStatus: String
)
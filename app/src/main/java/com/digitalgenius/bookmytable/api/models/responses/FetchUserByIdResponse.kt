package com.digitalgenius.bookmytable.api.models.responses


import com.digitalgenius.bookmytable.api.models.entities.User
import com.google.gson.annotations.SerializedName

data class FetchUserByIdResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("success")
    val success: Int,
    @SerializedName("user")
    val user: User
)
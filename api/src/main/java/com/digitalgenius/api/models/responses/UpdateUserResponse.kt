package com.digitalgenius.api.models.responses


import com.google.gson.annotations.SerializedName

data class UpdateUserResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("success")
    val success: Int
)
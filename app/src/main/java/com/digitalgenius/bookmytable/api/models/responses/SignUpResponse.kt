package com.digitalgenius.bookmytable.api.models.responses


import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("success")
    val success: Int,
    @SerializedName("id")
    val id:String?
)
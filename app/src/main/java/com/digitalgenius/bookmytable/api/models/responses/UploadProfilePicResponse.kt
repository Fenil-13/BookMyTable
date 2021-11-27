package com.digitalgenius.bookmytable.api.models.responses


import com.google.gson.annotations.SerializedName

data class UploadProfilePicResponse(
    @SerializedName("picture_filename")
    val pictureFilename: String,
    @SerializedName("success")
    val success: Int
)
package com.digitalgenius.bookmytable.api.models.responses


import com.google.gson.annotations.SerializedName

data class UploadRestaurantPicResponse(
    @SerializedName("success")
    val success: Int,
    @SerializedName("updated_files")
    val updatedFiles: List<String>
)
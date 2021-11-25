package com.digitalgenius.bookmytable.api.models.responses


import com.google.gson.annotations.SerializedName

data class OwnerGetRestaurantTableResponse(
    @SerializedName("success")
    val success: Int,
    @SerializedName("table_count")
    val tableCount: Int,
    @SerializedName("tableList")
    val tableList: List<TableResponse>
)
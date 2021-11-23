package com.digitalgenius.bookmytable.api.models.requests


import com.google.gson.annotations.SerializedName

data class GetTableByTypeRequest(
    @SerializedName("restaurant_id")
    val restaurantId: String,
    @SerializedName("table_type")
    val tableType: String
)
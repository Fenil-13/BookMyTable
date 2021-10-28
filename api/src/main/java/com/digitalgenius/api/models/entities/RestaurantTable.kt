package com.digitalgenius.api.models.entities


import com.google.gson.annotations.SerializedName

data class RestaurantTable(
    @SerializedName("available_table")
    val availableTable: Any?,
    @SerializedName("total_table")
    val totalTable: Any?,
    @SerializedName("type")
    val type: String
)
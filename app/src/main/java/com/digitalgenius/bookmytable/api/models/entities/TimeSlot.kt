package com.digitalgenius.bookmytable.api.models.entities


import com.google.gson.annotations.SerializedName

data class TimeSlot(
    @SerializedName("available_table")
    val availableTable: Int,
    @SerializedName("time")
    val time: String
)
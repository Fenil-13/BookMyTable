package com.digitalgenius.bookmytable.api.models.responses


import com.digitalgenius.bookmytable.api.models.entities.TimeSlot
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TableResponse(
    @SerializedName("_id")
    val id: String,
    @SerializedName("restaurant_id")
    val restaurantId: String,
    @SerializedName("table_count")
    val tableCount: Int,
    @SerializedName("table_type")
    val tableType: String,
    @SerializedName("time_slot")
    val timeSlot: List<TimeSlot>
):Serializable
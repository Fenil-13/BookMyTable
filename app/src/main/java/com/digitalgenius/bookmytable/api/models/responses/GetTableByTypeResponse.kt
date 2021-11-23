package com.digitalgenius.bookmytable.api.models.responses


import com.digitalgenius.bookmytable.api.models.entities.TimeSlot
import com.google.gson.annotations.SerializedName

data class GetTableByTypeResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("success")
    val success: Int?,
    @SerializedName("table_id")
    val tableId: String?,
    @SerializedName("time_slot")
    val timeSlot: List<TimeSlot>?
)
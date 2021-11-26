package com.digitalgenius.bookmytable.api.models.requests


import com.google.gson.annotations.SerializedName

data class UpdateRestaurantRequest(
    @SerializedName("restaurant_closing_time")
    val restaurantClosingTime: String,
    @SerializedName("restaurant_contact_number")
    val restaurantContactNumber: String,
    @SerializedName("restaurant_id")
    val restaurantId: String,
    @SerializedName("restaurant_location")
    val restaurantLocation: String,
    @SerializedName("restaurant_long_desc")
    val restaurantLongDesc: String,
    @SerializedName("restaurant_name")
    val restaurantName: String,
    @SerializedName("restaurant_opening_time")
    val restaurantOpeningTime: String,
    @SerializedName("restaurant_short_desc")
    val restaurantShortDesc: String
)
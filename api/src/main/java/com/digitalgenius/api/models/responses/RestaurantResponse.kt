package com.digitalgenius.api.models.responses


import com.digitalgenius.api.models.entities.RestaurantData
import com.google.gson.annotations.SerializedName

data class RestaurantResponse(
    @SerializedName("restaurant_data")
    val restaurantData: List<RestaurantData>?,
    @SerializedName("success")
    val success: Int
)
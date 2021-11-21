package com.digitalgenius.bookmytable.api.models.responses


import com.digitalgenius.bookmytable.api.models.entities.Restaurant
import com.google.gson.annotations.SerializedName

data class FetchRestaurantResponse(
    @SerializedName("restaurantList")
    val restaurantList: List<Restaurant>,
    @SerializedName("success")
    val success: Int
)
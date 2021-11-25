package com.digitalgenius.bookmytable.api.models.responses


import com.digitalgenius.bookmytable.api.models.entities.Restaurant
import com.google.gson.annotations.SerializedName

data class UserRestaurantsResponse(
    @SerializedName("restaurant_count")
    val restaurantCount: Int,
    @SerializedName("restaurantList")
    val restaurantList: List<Restaurant>,
    @SerializedName("success")
    val success: Int
)
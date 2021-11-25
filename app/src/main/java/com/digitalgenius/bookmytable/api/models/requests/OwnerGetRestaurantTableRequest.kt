package com.digitalgenius.bookmytable.api.models.requests


import com.google.gson.annotations.SerializedName

data class OwnerGetRestaurantTableRequest(
    @SerializedName("restaurant_id")
    val restaurantId: String
)
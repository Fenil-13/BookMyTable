package com.digitalgenius.bookmytable.api.models.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Restaurant(
    @SerializedName("_id")
    val id: String,
    @SerializedName("restaurant_closing_time")
    val restaurantClosingTime: String,
    @SerializedName("restaurant_contact_number")
    val restaurantContactNumber: String,
    @SerializedName("restaurant_location")
    val restaurantLocation: String,
    @SerializedName("restaurant_long_desc")
    val restaurantLongDesc: String,
    @SerializedName("restaurant_name")
    val restaurantName: String,
    @SerializedName("restaurant_opening_time")
    val restaurantOpeningTime: String,
    @SerializedName("restaurant_pics")
    val restaurantPics: List<String>,
    @SerializedName("restaurant_short_desc")
    val restaurantShortDesc: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("user_email")
    val userEmail: String?,
    @SerializedName("user_id")
    val userId: String?,
    @SerializedName("user_name")
    val userName: String?,
    @SerializedName("user_profile_pic")
    val userProfilePic: String?
):Serializable
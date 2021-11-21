package com.digitalgenius.bookmytable.repository

import com.digitalgenius.api.BMTClient

class RestaurantRepository {
    suspend fun getAllRestaurant()=
        BMTClient.publicApi.fetch_all_restaurant()
}
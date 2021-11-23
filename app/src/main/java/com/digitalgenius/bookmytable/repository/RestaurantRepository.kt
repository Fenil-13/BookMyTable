package com.digitalgenius.bookmytable.repository

import com.digitalgenius.api.BMTClient
import com.digitalgenius.bookmytable.api.models.requests.BookTableRequest
import com.digitalgenius.bookmytable.api.models.requests.CreateRestaurantRequest
import com.digitalgenius.bookmytable.api.models.requests.GetTableByTypeRequest
import com.digitalgenius.bookmytable.api.models.requests.UserBookingRequest

class RestaurantRepository {
    suspend fun getAllRestaurant()=
        BMTClient.publicApi.fetchAllRestaurant()

    suspend fun getTableByType(getTableByTypeRequest: GetTableByTypeRequest)=
        BMTClient.publicApi.getTableByType(getTableByTypeRequest)

    suspend fun createRestaurant(createRestaurantRequest: CreateRestaurantRequest)=
        BMTClient.publicApi.createRestaurant(createRestaurantRequest)

    suspend fun bookTable(bookTableRequest: BookTableRequest)=
        BMTClient.publicApi.bookTable(bookTableRequest)

    suspend fun getUserBookingHistory(userBookingRequest: UserBookingRequest)=
        BMTClient.publicApi.userBookingHistory(userBookingRequest);
}
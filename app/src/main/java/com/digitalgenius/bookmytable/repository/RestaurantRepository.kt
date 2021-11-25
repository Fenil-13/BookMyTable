package com.digitalgenius.bookmytable.repository

import com.digitalgenius.api.BMTClient
import com.digitalgenius.bookmytable.api.models.requests.*

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

    suspend fun getMyRestaurant(userBookingRequest: UserBookingRequest)=
        BMTClient.publicApi.userGetRestaurant(userBookingRequest);


    suspend fun getOwnerGetRestaurantTables(ownerGetRestaurantTableRequest: OwnerGetRestaurantTableRequest)=
        BMTClient.publicApi.ownerGetRestaurantTables(ownerGetRestaurantTableRequest);

    suspend fun getBookingListByRestaurantId(ownerGetRestaurantTableRequest: OwnerGetRestaurantTableRequest)=
        BMTClient.publicApi.bookingListByRestaurantId(ownerGetRestaurantTableRequest);

    suspend fun orderCompleted(orderCompletedRequest: OrderCompletedRequest)=
        BMTClient.publicApi.orderCompleted(orderCompletedRequest);


    suspend fun addTable(addTableRequest: AddTableRequest)=
        BMTClient.publicApi.addTable(addTableRequest);
}
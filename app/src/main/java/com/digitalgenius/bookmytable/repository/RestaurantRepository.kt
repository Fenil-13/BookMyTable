package com.digitalgenius.bookmytable.repository

import com.digitalgenius.api.BMTClient
import com.digitalgenius.bookmytable.api.models.requests.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

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

    suspend fun updateRestaurant(updateRestaurantRequest: UpdateRestaurantRequest)=
        BMTClient.publicApi.updateRestaurant(updateRestaurantRequest);

    suspend fun uploadRestaurantPics(restaurant_id: RequestBody,
                                     picture_type: RequestBody,
                                     pic1 : MultipartBody.Part,
                                     pic2 : MultipartBody.Part,
                                     pic3 : MultipartBody.Part,
                                     pic4 : MultipartBody.Part)=
        BMTClient.publicApi.uploadRestaurantPics(restaurant_id,picture_type,pic1, pic2, pic3, pic4);
}
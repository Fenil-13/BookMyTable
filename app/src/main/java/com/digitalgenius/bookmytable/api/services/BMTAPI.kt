package com.digitalgenius.api.services

import com.digitalgenius.bookmytable.api.models.requests.*
import com.digitalgenius.bookmytable.api.models.responses.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface BMTAPI {

    //User
    @POST("login_user")
    suspend fun loginUser(
       @Body loginUserRequest: loginUserRequest
    ): Response<LoginUserResponse>

    @POST("sign_up_user")
    suspend fun signUpUser(
        @Body singUpRequest: SignUpUserRequest
    ):Response<SignUpResponse>


    //Restaurant
    @GET("fetch_all_restaurant")
    suspend fun fetchAllRestaurant(
    ):Response<FetchRestaurantResponse>

    @POST("get_table_by_type")
    suspend fun getTableByType(
        @Body getTableByTypeRequest: GetTableByTypeRequest
    ):Response<GetTableByTypeResponse>


    @POST("create_restaurant")
    suspend fun createRestaurant(
        @Body createRestaurantRequest: CreateRestaurantRequest
    ):Response<GeneralResponse>

    @POST("book_table")
    suspend fun bookTable(
        @Body bookTableRequest: BookTableRequest
    ):Response<BookTableResponse>


    @POST("booking_history")
    suspend fun userBookingHistory(
        @Body userBookingRequest: UserBookingRequest
    ):Response<UserBookingHistoryResponse>



    @POST("user_get_restaurants")
    suspend fun userGetRestaurant(
        @Body userBookingRequest: UserBookingRequest
    ):Response<UserRestaurantsResponse>


    @POST("owner_get_restaurant_tables")
    suspend fun ownerGetRestaurantTables(
        @Body ownerGetRestaurantTableRequest: OwnerGetRestaurantTableRequest
    ):Response<OwnerGetRestaurantTableResponse>


    @POST("booking_list_by_restaurant_id")
    suspend fun bookingListByRestaurantId(
        @Body ownerGetRestaurantTableRequest: OwnerGetRestaurantTableRequest
    ):Response<RestaurantBookingHistoryResponse>


    @POST("order_completed")
    suspend fun orderCompleted(
        @Body orderCompletedRequest: OrderCompletedRequest
    ):Response<OrderCompletedResponse>

    @POST("add_tables")
    suspend fun addTable(
        @Body addTableRequest: AddTableRequest
    ):Response<GeneralResponse>

    @POST("update_restaurant")
    suspend fun updateRestaurant(
        @Body updateRestaurantRequest: UpdateRestaurantRequest
    ):Response<GeneralResponse>

    @POST("update_user")
    suspend fun updateUser(
        @Body userUpdateRequest: UserUpdateRequest
    ):Response<GeneralResponse>

    @POST("fetch_user_by_id")
    suspend fun fetchUserById(
        @Body userBookingRequest: UserBookingRequest
    ): Response<FetchUserByIdResponse>

    @Multipart
    @POST("upload_pic")
    suspend fun uploadPic(
        @Part("user_id") user_id: RequestBody,
        @Part("picture_type") picture_type: RequestBody,
        @Part picture_file : MultipartBody.Part
    ):Response<UploadProfilePicResponse>

    @Multipart
    @POST("uplod_restaurant_pics")
    suspend fun uploadRestaurantPics(
        @Part("restaurant_id") restaurant_id: RequestBody,
        @Part("picture_type") picture_type: RequestBody,
        @Part pic1 : MultipartBody.Part,
        @Part pic2 : MultipartBody.Part,
        @Part pic3 : MultipartBody.Part,
        @Part pic4 : MultipartBody.Part
    ):Response<UploadRestaurantPicResponse>
}
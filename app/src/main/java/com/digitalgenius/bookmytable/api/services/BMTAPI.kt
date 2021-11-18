package com.digitalgenius.api.services

import com.digitalgenius.api.models.requests.CreateRestaurantRequest
import com.digitalgenius.api.models.requests.SignUpRequest
import com.digitalgenius.api.models.requests.UpdateUserRequest
import com.digitalgenius.api.models.responses.GeneralResponse
import com.digitalgenius.api.models.responses.LoginUserResponse
import com.digitalgenius.api.models.responses.RestaurantResponse
import retrofit2.Response
import retrofit2.http.*

interface BMTAPI {

    @GET("login_user")
    suspend fun loginUser(
       @Query("user_phone_number") user_phone_number: String,
       @Query("user_password") user_password: String
    ): Response<LoginUserResponse>



    @POST("sign_up_user")
    suspend fun signUpUser(
        @Body singUpRequest: SignUpRequest
    ):Response<GeneralResponse>


    @POST("update_user")
    suspend fun updateUser(
        @Body updateUserRequest: UpdateUserRequest
    ):Response<GeneralResponse>

    @POST("create_restaurant")
    suspend fun createRestaurant(
        @Body createRestaurantRequest: CreateRestaurantRequest
    ):Response<GeneralResponse>


    @GET("get_restaurants")
    suspend fun getRestaurants(
        @Query("user_id") user_id:String
    ):Response<RestaurantResponse>
}
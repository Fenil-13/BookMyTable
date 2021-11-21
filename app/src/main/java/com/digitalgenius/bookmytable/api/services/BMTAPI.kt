package com.digitalgenius.api.services

import com.digitalgenius.bookmytable.api.models.requests.SignUpUserRequest
import com.digitalgenius.bookmytable.api.models.requests.loginUserRequest
import com.digitalgenius.bookmytable.api.models.responses.FetchRestaurantResponse
import com.digitalgenius.bookmytable.api.models.responses.GeneralResponse
import com.digitalgenius.bookmytable.api.models.responses.LoginUserResponse
import com.digitalgenius.bookmytable.api.models.responses.SignUpResponse
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
    suspend fun fetch_all_restaurant(
    ):Response<FetchRestaurantResponse>


}
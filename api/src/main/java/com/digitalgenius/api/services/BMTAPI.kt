package com.digitalgenius.api.services

import com.digitalgenius.api.models.responses.LoginUserResponse
import com.digitalgenius.api.models.responses.SignUpUserResponse
import com.digitalgenius.api.models.requests.SignUpRequest
import com.digitalgenius.api.models.requests.UpdateUserRequest
import com.digitalgenius.api.models.responses.UpdateUserResponse
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
    ):Response<SignUpUserResponse>


    @POST("update_user")
    suspend fun updateUser(
        @Body updateUserRequest: UpdateUserRequest
    ):Response<UpdateUserResponse>
}
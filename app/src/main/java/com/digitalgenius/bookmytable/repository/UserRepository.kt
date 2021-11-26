package com.digitalgenius.bookmytable.repository

import com.digitalgenius.api.BMTClient
import com.digitalgenius.bookmytable.api.models.requests.SignUpUserRequest
import com.digitalgenius.bookmytable.api.models.requests.UserBookingRequest
import com.digitalgenius.bookmytable.api.models.requests.UserUpdateRequest
import com.digitalgenius.bookmytable.api.models.requests.loginUserRequest

class UserRepository {

    suspend fun loginUser(loginUserRequest: loginUserRequest)=
        BMTClient.publicApi.loginUser(loginUserRequest)

    suspend fun createUser(signUpUserRequest: SignUpUserRequest)=
        BMTClient.publicApi.signUpUser(signUpUserRequest)


    suspend fun updateUser(userUpdateRequest: UserUpdateRequest)=
        BMTClient.publicApi.updateUser(userUpdateRequest)

    suspend fun fetchUserById(userBookingRequest: UserBookingRequest)=
        BMTClient.publicApi.fetchUserById(userBookingRequest)
}
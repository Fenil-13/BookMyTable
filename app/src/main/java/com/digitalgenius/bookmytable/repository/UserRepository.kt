package com.digitalgenius.bookmytable.repository

import com.digitalgenius.api.BMTClient
import com.digitalgenius.bookmytable.api.models.requests.SignUpUserRequest
import com.digitalgenius.bookmytable.api.models.requests.UserBookingRequest
import com.digitalgenius.bookmytable.api.models.requests.UserUpdateRequest
import com.digitalgenius.bookmytable.api.models.requests.loginUserRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserRepository {

    suspend fun loginUser(loginUserRequest: loginUserRequest) =
        BMTClient.publicApi.loginUser(loginUserRequest)

    suspend fun createUser(signUpUserRequest: SignUpUserRequest) =
        BMTClient.publicApi.signUpUser(signUpUserRequest)


    suspend fun updateUser(userUpdateRequest: UserUpdateRequest) =
        BMTClient.publicApi.updateUser(userUpdateRequest)

    suspend fun fetchUserById(userBookingRequest: UserBookingRequest) =
        BMTClient.publicApi.fetchUserById(userBookingRequest)

    suspend fun uploadPic(
        user_id: RequestBody,
        picture_type: RequestBody,
        picture_file: MultipartBody.Part
    ) =
        BMTClient.publicApi.uploadPic(user_id,picture_type, picture_file)
}
package com.digitalgenius.bookmytable.ui.Home_A

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.digitalgenius.bookmytable.api.models.requests.UserBookingRequest
import com.digitalgenius.bookmytable.api.models.requests.UserUpdateRequest
import com.digitalgenius.bookmytable.api.models.responses.*
import com.digitalgenius.bookmytable.repository.RestaurantRepository
import com.digitalgenius.bookmytable.repository.UserRepository
import com.digitalgenius.bookmytable.utils.Constants
import com.digitalgenius.bookmytable.utils.Constants.Companion.TAG
import com.digitalgenius.bookmytable.utils.Functions
import com.digitalgenius.bookmytable.utils.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.IOException

class RestaurantViewModel(
    application: Application,
    val restaurantRepository: RestaurantRepository,
    val userRepository: UserRepository
) : AndroidViewModel(application) {
    val allRestaurant: MutableLiveData<Resource<FetchRestaurantResponse>> =
        MutableLiveData()

    val userBookingHistory: MutableLiveData<Resource<UserBookingHistoryResponse>> =
        MutableLiveData()

    val userUpdateResponse: MutableLiveData<Resource<GeneralResponse>> =
        MutableLiveData()

    val fetchUserByIdResponse: MutableLiveData<Resource<FetchUserByIdResponse>> =
        MutableLiveData()


    val uploadProfilePicResponse: MutableLiveData<Resource<UploadProfilePicResponse>> =
        MutableLiveData()

    init {
        get_all_restaurant()
        val userBookingRequest = UserBookingRequest(Constants.Companion.userData?.userId!!)
        get_user_booking_history(userBookingRequest)
    }


    fun get_all_restaurant() = viewModelScope.launch {
        safeGetAllRestaurantCall()
    }


    private suspend fun safeGetAllRestaurantCall() {
        allRestaurant.postValue(Resource.Loading())
        try {
            val response = restaurantRepository.getAllRestaurant()
            allRestaurant.postValue(handleAllRestauntResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> allRestaurant.postValue(Resource.Error("Network Failure"))
                else -> allRestaurant.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleAllRestauntResponse(response: Response<FetchRestaurantResponse>):
            Resource<FetchRestaurantResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun get_user_booking_history(userBookingRequest: UserBookingRequest) = viewModelScope.launch {
        safeUserBookingHisoryCall(userBookingRequest)
    }

    private suspend fun safeUserBookingHisoryCall(userBookingRequest: UserBookingRequest) {
        userBookingHistory.postValue(Resource.Loading())
        try {
            val response = restaurantRepository.getUserBookingHistory(userBookingRequest)
            userBookingHistory.postValue(handleUserBookingHistoryResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> userBookingHistory.postValue(Resource.Error("Network Failure"))
                else -> userBookingHistory.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    private fun handleUserBookingHistoryResponse(response: Response<UserBookingHistoryResponse>):
            Resource<UserBookingHistoryResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun updateUser(userUpdateRequest: UserUpdateRequest) = viewModelScope.launch {
        safeUpdateUserCall(userUpdateRequest)
    }

    private suspend fun safeUpdateUserCall(userUpdateRequest: UserUpdateRequest) {
        userUpdateResponse.postValue(Resource.Loading())
        try {
            val response = userRepository.updateUser(userUpdateRequest)
            userUpdateResponse.postValue(handleUpdateUserResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> userUpdateResponse.postValue(Resource.Error("Network Failure"))
                else -> userUpdateResponse.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    private fun handleUpdateUserResponse(response: Response<GeneralResponse>):
            Resource<GeneralResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun fetchUserById(userBookingRequest: UserBookingRequest) = viewModelScope.launch {
        safeFetchUserByIdCall(userBookingRequest)
    }

    private suspend fun safeFetchUserByIdCall(userBookingRequest: UserBookingRequest) {
        fetchUserByIdResponse.postValue(Resource.Loading())
        try {
            val response = userRepository.fetchUserById(userBookingRequest)
            fetchUserByIdResponse.postValue(handleFetchUserByIdResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> fetchUserByIdResponse.postValue(Resource.Error("Network Failure"))
                else -> fetchUserByIdResponse.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    private fun handleFetchUserByIdResponse(response: Response<FetchUserByIdResponse>):
            Resource<FetchUserByIdResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(resultResponse.status.equals("user found")){
                    return Resource.Success(resultResponse)
                }else{
                    return Resource.Error(resultResponse.status)
                }

            }
        }
        return Resource.Error(response.message())
    }

fun uploadPic(user_id: RequestBody,
              picture_type: RequestBody,
              picture_file:  MultipartBody.Part) = viewModelScope.launch {
    safeUploadPicCall(user_id,picture_type, picture_file)
    }

    private suspend fun safeUploadPicCall(user_id: RequestBody,
                                          picture_type: RequestBody,
                                          picture_file:  MultipartBody.Part) {
        uploadProfilePicResponse.postValue(Resource.Loading())
        try {
            Log.d(TAG, "safeUploadPicCall: "+picture_file)
            val response = userRepository.uploadPic(user_id,picture_type, picture_file)
            Log.d(TAG, "safeUploadPicCall: "+response)
            uploadProfilePicResponse.postValue(handleUploadPicResponse(response))
        } catch (t: Throwable) {
            Log.d(TAG, "safeUploadPicCall: "+t.message)
            when (t) {
                is IOException -> uploadProfilePicResponse.postValue(Resource.Error("Network Failure"))
                else -> uploadProfilePicResponse.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    private fun handleUploadPicResponse(response: Response<UploadProfilePicResponse>):
            Resource<UploadProfilePicResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


}
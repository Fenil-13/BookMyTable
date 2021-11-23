package com.digitalgenius.bookmytable.ui.Home_A

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.digitalgenius.bookmytable.api.models.requests.UserBookingRequest
import com.digitalgenius.bookmytable.api.models.responses.FetchRestaurantResponse
import com.digitalgenius.bookmytable.api.models.responses.UserBookingHistoryResponse
import com.digitalgenius.bookmytable.repository.RestaurantRepository
import com.digitalgenius.bookmytable.utils.Constants
import com.digitalgenius.bookmytable.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class RestaurantViewModel(
    application: Application,
    val restaurantRepository: RestaurantRepository
) : AndroidViewModel(application) {
    val allRestaurant: MutableLiveData<Resource<FetchRestaurantResponse>> =
        MutableLiveData()

    val userBookingHistory: MutableLiveData<Resource<UserBookingHistoryResponse>> =
        MutableLiveData()

    init {
        get_all_restaurant()
        val userBookingRequest=UserBookingRequest(Constants.Companion.userData?.userId!!)
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


}
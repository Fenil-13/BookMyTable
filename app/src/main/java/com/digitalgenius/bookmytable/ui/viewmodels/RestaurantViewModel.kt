package com.digitalgenius.bookmytable.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.digitalgenius.bookmytable.api.models.requests.loginUserRequest
import com.digitalgenius.bookmytable.api.models.responses.FetchRestaurantResponse
import com.digitalgenius.bookmytable.repository.RestaurantRepository
import com.digitalgenius.bookmytable.repository.UserRepository
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

    init {
        get_all_restaurant()
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


}
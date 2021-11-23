package com.digitalgenius.bookmytable.ui.MyRestaurant_A

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.digitalgenius.bookmytable.api.models.requests.CreateRestaurantRequest
import com.digitalgenius.bookmytable.api.models.responses.FetchRestaurantResponse
import com.digitalgenius.bookmytable.api.models.responses.GeneralResponse
import com.digitalgenius.bookmytable.repository.RestaurantRepository
import com.digitalgenius.bookmytable.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MyRestaurantViewModel(app: Application, val restaurantRepository: RestaurantRepository) :
    AndroidViewModel(app) {


    val createRestaurantResponse: MutableLiveData<Resource<GeneralResponse>> =
        MutableLiveData()


    fun createRestaurant(createRestaurantRequest: CreateRestaurantRequest) = viewModelScope.launch {
        safeCreateRestaurantCall(createRestaurantRequest)
    }

    private suspend fun safeCreateRestaurantCall(createRestaurantRequest: CreateRestaurantRequest) {
        createRestaurantResponse.postValue(Resource.Loading())
        try {
            val response = restaurantRepository.createRestaurant(createRestaurantRequest)
            createRestaurantResponse.postValue(handleCreateRestaurantResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> createRestaurantResponse.postValue(Resource.Error("Network Failure"))
                else -> createRestaurantResponse.postValue(Resource.Error("Conversion Error"))
            }
        }

    }

    private fun handleCreateRestaurantResponse(response: Response<GeneralResponse>): Resource<GeneralResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(resultResponse.status=="restaurant_created"){
                    return Resource.Success(resultResponse)
                }else{
                    return Resource.Error(resultResponse.status)
                }

            }
        }
        return Resource.Error(response.message())
    }
}
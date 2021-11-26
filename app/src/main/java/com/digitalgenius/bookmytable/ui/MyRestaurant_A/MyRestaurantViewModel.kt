package com.digitalgenius.bookmytable.ui.MyRestaurant_A

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.digitalgenius.bookmytable.api.models.requests.*
import com.digitalgenius.bookmytable.api.models.responses.*
import com.digitalgenius.bookmytable.repository.RestaurantRepository
import com.digitalgenius.bookmytable.utils.Constants
import com.digitalgenius.bookmytable.utils.Constants.Companion.TAG
import com.digitalgenius.bookmytable.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MyRestaurantViewModel(app: Application, val restaurantRepository: RestaurantRepository) :
    AndroidViewModel(app) {


    val createRestaurantResponse: MutableLiveData<Resource<GeneralResponse>> =
        MutableLiveData()

    val myRestaurantResponse: MutableLiveData<Resource<UserRestaurantsResponse>> =
        MutableLiveData()


    val ownerGetRestaurantTableResponse: MutableLiveData<Resource<OwnerGetRestaurantTableResponse>> =
        MutableLiveData()

    val restaurantBookingHistory: MutableLiveData<Resource<RestaurantBookingHistoryResponse>> =
        MutableLiveData()

    val orderCompletedResponse: MutableLiveData<Resource<OrderCompletedResponse>> =
        MutableLiveData()

    val addTableResponse: MutableLiveData<Resource<GeneralResponse>> =
        MutableLiveData()

    val updateRestaurantResponse: MutableLiveData<Resource<GeneralResponse>> =
        MutableLiveData()

    init {
        val userBookingRequest = UserBookingRequest(Constants.userData!!.userId)
        getMyRestaurant(userBookingRequest)
    }


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
                if (resultResponse.status == "restaurant_created") {
                    return Resource.Success(resultResponse)
                } else {
                    return Resource.Error(resultResponse.status)
                }

            }
        }
        return Resource.Error(response.message())
    }

    fun getMyRestaurant(userBookingRequest: UserBookingRequest) = viewModelScope.launch {
        safeGetMyRestaurantCall(userBookingRequest)
    }

    private suspend fun safeGetMyRestaurantCall(userBookingRequest: UserBookingRequest) {
        myRestaurantResponse.postValue(Resource.Loading())
        try {
            val response = restaurantRepository.getMyRestaurant(userBookingRequest)
            myRestaurantResponse.postValue(handleGetMyRestaurantResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> myRestaurantResponse.postValue(Resource.Error("Network Failure"))
                else -> myRestaurantResponse.postValue(Resource.Error("Conversion Error"))
            }
        }

    }

    private fun handleGetMyRestaurantResponse(response: Response<UserRestaurantsResponse>): Resource<UserRestaurantsResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)

            }
        }
        return Resource.Error(response.message())
    }


    fun getOwnerGetRestaurantTables(ownerGetRestaurantTableRequest: OwnerGetRestaurantTableRequest) =
        viewModelScope.launch {
            safeOwnerGetRestaurantTablesCall(ownerGetRestaurantTableRequest)
        }

    private suspend fun safeOwnerGetRestaurantTablesCall(ownerGetRestaurantTableRequest: OwnerGetRestaurantTableRequest) {
        ownerGetRestaurantTableResponse.postValue(Resource.Loading())
        try {
            val response =
                restaurantRepository.getOwnerGetRestaurantTables(ownerGetRestaurantTableRequest)
            ownerGetRestaurantTableResponse.postValue(
                handleGetOwnerGetRestaurantTablesResponse(
                    response
                )
            )
        } catch (t: Throwable) {
            when (t) {
                is IOException -> ownerGetRestaurantTableResponse.postValue(Resource.Error("Network Failure"))
                else -> ownerGetRestaurantTableResponse.postValue(Resource.Error("Conversion Error"))
            }
        }

    }

    private fun handleGetOwnerGetRestaurantTablesResponse(response: Response<OwnerGetRestaurantTableResponse>): Resource<OwnerGetRestaurantTableResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)

            }
        }
        return Resource.Error(response.message())
    }


    fun getBookingListByRestaurantId(ownerGetRestaurantTableRequest: OwnerGetRestaurantTableRequest) =
        viewModelScope.launch {
            safeGetBookingListByRestaurantIdCall(ownerGetRestaurantTableRequest)
        }

    private suspend fun safeGetBookingListByRestaurantIdCall(ownerGetRestaurantTableRequest: OwnerGetRestaurantTableRequest) {
        restaurantBookingHistory.postValue(Resource.Loading())
        try {
            val response =
                restaurantRepository.getBookingListByRestaurantId(ownerGetRestaurantTableRequest)
            Log.d(TAG, "safeGetBookingListByRestaurantIdCall: "+response.body())

            restaurantBookingHistory.postValue(
                handleGetBookingListByRestaurantIdResponse(response)
            )
        } catch (t: Throwable) {
            when (t) {
                is IOException -> restaurantBookingHistory.postValue(Resource.Error("Network Failure"))
                else -> restaurantBookingHistory.postValue(Resource.Error("Conversion Error"))
            }
        }

    }

    private fun handleGetBookingListByRestaurantIdResponse(response: Response<RestaurantBookingHistoryResponse>): Resource<RestaurantBookingHistoryResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun orderCompleted(orderCompletedRequest: OrderCompletedRequest) =
        viewModelScope.launch {
            safeOrderCompletedCall(orderCompletedRequest)
        }

    private suspend fun safeOrderCompletedCall(orderCompletedRequest: OrderCompletedRequest) {
        orderCompletedResponse.postValue(Resource.Loading())
        try {
            val response =
                restaurantRepository.orderCompleted(orderCompletedRequest)

            orderCompletedResponse.postValue(
                handleOrderCompletedResponse(response)
            )
        } catch (t: Throwable) {
            when (t) {
                is IOException -> orderCompletedResponse.postValue(Resource.Error("Network Failure"))
                else -> orderCompletedResponse.postValue(Resource.Error("Conversion Error"))
            }
        }

    }

    private fun handleOrderCompletedResponse(response: Response<OrderCompletedResponse>): Resource<OrderCompletedResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun addTable(addTableRequest: AddTableRequest) =
        viewModelScope.launch {
            safeAddTableCall(addTableRequest)
        }

    private suspend fun safeAddTableCall(addTableRequest: AddTableRequest) {
        addTableResponse.postValue(Resource.Loading())
        try {
            val response =
                restaurantRepository.addTable(addTableRequest)

            addTableResponse.postValue(
                handleAddTableResponse(response)
            )
        } catch (t: Throwable) {
            when (t) {
                is IOException -> addTableResponse.postValue(Resource.Error("Network Failure"))
                else -> addTableResponse.postValue(Resource.Error("Conversion Error"))
            }
        }

    }

    private fun handleAddTableResponse(response: Response<GeneralResponse>): Resource<GeneralResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun updateRestaurant(updateRestaurantRequest: UpdateRestaurantRequest) =
        viewModelScope.launch {
            safeUpdateRestaurantCall(updateRestaurantRequest)
        }

    private suspend fun safeUpdateRestaurantCall(updateRestaurantRequest: UpdateRestaurantRequest) {
        updateRestaurantResponse.postValue(Resource.Loading())
        try {
            val response =
                restaurantRepository.updateRestaurant(updateRestaurantRequest)

            updateRestaurantResponse.postValue(
                handleUpdateRestaurantResponse(response)
            )
        } catch (t: Throwable) {
            when (t) {
                is IOException -> updateRestaurantResponse.postValue(Resource.Error("Network Failure"))
                else -> updateRestaurantResponse.postValue(Resource.Error("Conversion Error"))
            }
        }

    }

    private fun handleUpdateRestaurantResponse(response: Response<GeneralResponse>): Resource<GeneralResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}
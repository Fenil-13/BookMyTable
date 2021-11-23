package com.digitalgenius.bookmytable.ui.TableBooking_A

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.digitalgenius.bookmytable.api.models.requests.BookTableRequest
import com.digitalgenius.bookmytable.api.models.requests.GetTableByTypeRequest
import com.digitalgenius.bookmytable.api.models.responses.BookTableResponse
import com.digitalgenius.bookmytable.api.models.responses.GetTableByTypeResponse
import com.digitalgenius.bookmytable.repository.RestaurantRepository
import com.digitalgenius.bookmytable.utils.Constants
import com.digitalgenius.bookmytable.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class BookingViewModel(app: Application, val restaurantRepository: RestaurantRepository) :
    AndroidViewModel(app) {
    val tableByType: MutableLiveData<Resource<GetTableByTypeResponse>> =
        MutableLiveData()

    val bookTableResponse: MutableLiveData<Resource<BookTableResponse>> =
        MutableLiveData()

    fun get_table_by_type(getTableByTypeRequest: GetTableByTypeRequest) = viewModelScope.launch {
        safeGetTableByType(getTableByTypeRequest)
    }

    private suspend fun safeGetTableByType(getTableByTypeRequest: GetTableByTypeRequest) {
        tableByType.postValue(Resource.Loading())
        Log.d(Constants.TAG, "safeGetTableByType:")
        try {
            val response = restaurantRepository.getTableByType(getTableByTypeRequest)
            Log.d(Constants.TAG, "safeGetTableByType:response $response")
            Log.d(Constants.TAG, "safeGetTableByType: ${response.body()!!.timeSlot!!.size}")
            tableByType.postValue(handleGetTableByTypeResponse(response))
        } catch (t: Throwable) {
            Log.d(Constants.TAG, "safeGetTableByType: ${t.message}")
            when (t) {
                is IOException -> tableByType.postValue(Resource.Error("Network Failure"))
                else -> tableByType.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleGetTableByTypeResponse(response: Response<GetTableByTypeResponse>):
            Resource<GetTableByTypeResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (resultResponse.status?.equals("table_not_available") == true) {
                    return Resource.Error("No Available Table")
                } else if (resultResponse.status?.equals("table_available") == true) {
                    return Resource.Success(resultResponse)
                }
            }
        }
        return Resource.Error(response.message())
    }


    fun book_table(bookTableRequest: BookTableRequest) = viewModelScope.launch {
        safeBookTableCall(bookTableRequest)
    }

    private suspend fun safeBookTableCall(bookTableRequest: BookTableRequest) {
        bookTableResponse.postValue(Resource.Loading())
        try {
            val response = restaurantRepository.bookTable(bookTableRequest)
            bookTableResponse.postValue(handleBookTableResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> bookTableResponse.postValue(Resource.Error("Network Failure"))
                else -> bookTableResponse.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleBookTableResponse(response: Response<BookTableResponse>):
            Resource<BookTableResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (resultResponse.status?.equals("Not Booked") == true) {
                    return Resource.Error("Technical Error,Your Order is not booked")
                } else if (resultResponse.status?.equals("Booked") == true) {
                    return Resource.Success(resultResponse)
                }
            }
        }
        return Resource.Error(response.message())
    }
}
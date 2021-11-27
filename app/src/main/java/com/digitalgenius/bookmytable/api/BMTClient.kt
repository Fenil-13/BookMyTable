package com.digitalgenius.api

import com.digitalgenius.api.services.BMTAPI
import com.digitalgenius.bookmytable.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BMTClient {
    val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    val publicApi = retrofitBuilder.build().create(BMTAPI::class.java)


}
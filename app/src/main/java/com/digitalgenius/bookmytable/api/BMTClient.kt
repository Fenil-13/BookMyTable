package com.digitalgenius.api

import com.digitalgenius.api.services.BMTAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BMTClient {
    val BASE_URL="http://192.168.0.106:5000/"
    val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    val publicApi = retrofitBuilder.build().create(BMTAPI::class.java)


}
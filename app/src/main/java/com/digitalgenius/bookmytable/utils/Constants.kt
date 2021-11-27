package com.digitalgenius.bookmytable.utils

import android.net.Uri
import com.digitalgenius.bookmytable.api.models.entities.Restaurant
import com.digitalgenius.bookmytable.api.models.entities.UserData
import java.io.File

class Constants {
    companion object{
        const val TAG="Fenil"
        const val API_KEY="fda0903d9de847fb89e323c2744559bd"
        const val BASE_URL="http://192.168.0.106:5000/"
        const val deviceToken="myToken"
        const val SEARCH_NEWS_TIME_DELAY=500L
        const val QUERY_PAGE_SIZE=20
        var userData:UserData?=null
        var isSelectedProfilePic=false;
        var profilePic: File?=null;
        var restaurantData: Restaurant?=null;
    }
}
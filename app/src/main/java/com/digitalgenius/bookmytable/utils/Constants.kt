package com.digitalgenius.bookmytable.utils

import com.digitalgenius.bookmytable.api.models.entities.UserData

class Constants {
    companion object{
        const val TAG="Fenil"
        const val API_KEY="fda0903d9de847fb89e323c2744559bd"
        const val BASE_URL="https://newsapi.org"
        const val deviceToken="myToken"
        const val SEARCH_NEWS_TIME_DELAY=500L
        const val QUERY_PAGE_SIZE=20
        var userData:UserData?=null
    }
}
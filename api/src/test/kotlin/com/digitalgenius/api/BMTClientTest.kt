package com.digitalgenius.api

import com.digitalgenius.api.models.requests.CreateRestaurantRequest
import com.digitalgenius.api.models.requests.SignUpRequest
import com.digitalgenius.api.models.requests.UpdateUserRequest
import kotlinx.coroutines.runBlocking
import org.junit.Test

class BMTClientTest {

    @Test
    fun `LOGIN USER`() {
        runBlocking {
            val loginUserResponse = BMTClient.publicApi.
            loginUser("9909596506","Mihir@123").body()!!
            println(loginUserResponse.userStatus)
            println(loginUserResponse.userData!!.userName)
        }

    }


    @Test
    fun `SIGN UP USER`(){
        runBlocking {
            val signUpRequest=SignUpRequest(
                "rtueitueter",
                "retertretetr",
                "princevekariya@gmail.com",
                "Surat",
                "princeDevil",
                "Prince@123",
                                "9723995961"
            )
            val signUpUserResponse=BMTClient.publicApi.signUpUser(signUpRequest).body()!!
            println(signUpUserResponse.status)
            println(signUpUserResponse.success)

        }
    }


    @Test
    fun `UPDATE USER`(){
        runBlocking {
            val updateUserRequest=UpdateUserRequest(
                "skjdsfsdkfafasdas",
                "jjkjiininin",
                "mihirsangani@gmail.com",
                "6138bf748a5e25e753b1fa4c",
                "Rajkot",
                "mihirSarlaBhabhi",
                "Mihir@123",
                "9909596506",
                ""
            )
            val generalResponse=BMTClient.publicApi.updateUser(updateUserRequest).body()!!
            println(generalResponse.status)
            println(generalResponse.success)

        }
    }

    @Test
    fun `CREATE RESTAURANT`(){
        runBlocking {
            val createRestaurantRequest=CreateRestaurantRequest(
                "10:30 PM",
                "9600123564",
                "Sarthana,Surat",
                "longggg idea",
                "Gotalo",
                "8:30 AM",
                "Here milta hai",
                "6138d40a410f2b02ac13c3f7"
            )
            val signUpUserResponse=BMTClient.publicApi.createRestaurant(createRestaurantRequest).body()!!
            println(signUpUserResponse.status)
            println(signUpUserResponse.success)

        }
    }


    @Test
    fun `GET RESTAURANT`(){
        runBlocking {
            val restaurantResponse=BMTClient.publicApi.getRestaurants("").body()!!
            println(restaurantResponse.restaurantData!!.size)
        }
    }
}
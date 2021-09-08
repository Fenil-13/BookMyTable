package com.digitalgenius.api

import com.digitalgenius.api.models.entities.UserData
import com.digitalgenius.api.models.requests.SignUpRequest
import com.digitalgenius.api.models.requests.UpdateUserRequest
import junit.framework.Assert.assertNotNull
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
            val signUpUserResponse=BMTClient.publicApi.updateUser(updateUserRequest).body()!!
            println(signUpUserResponse.status)
            println(signUpUserResponse.success)

        }
    }
}
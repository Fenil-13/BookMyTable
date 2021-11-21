package com.digitalgenius.bookmytable.ui.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.digitalgenius.bookmytable.api.models.requests.SignUpUserRequest
import com.digitalgenius.bookmytable.api.models.requests.loginUserRequest
import com.digitalgenius.bookmytable.api.models.responses.LoginUserResponse
import com.digitalgenius.bookmytable.api.models.responses.SignUpResponse
import com.digitalgenius.bookmytable.repository.UserRepository
import com.digitalgenius.bookmytable.utils.Constants
import com.digitalgenius.bookmytable.utils.Resource
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

class PhoneAuthViewModel(application: Application,val userRepository: UserRepository) : AndroidViewModel(application) {
    val isOtpSend: MutableLiveData<String> = MutableLiveData<String>("Not Called")
    val isVerificationDone: MutableLiveData<String> = MutableLiveData<String>("Not Called")
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var storedVerificationId: String? = null
    var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    lateinit var firebaseUser:FirebaseUser
    val userData :MutableLiveData<Resource<LoginUserResponse>> = MutableLiveData<Resource<LoginUserResponse>>()
    val signUpResponse :MutableLiveData<Resource<SignUpResponse>> = MutableLiveData<Resource<SignUpResponse>>()
    init {
        isOtpSend.value = "Not Called"
        isVerificationDone.value = "Not Called"
    }

    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // This method is called when the verification is completed
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                auth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            isVerificationDone.postValue("Done")
                        } else {
                            isVerificationDone.postValue("Failed")
                            isOtpSend.postValue("Done")
                        }
                    }.addOnFailureListener {
                        isVerificationDone.postValue("Failed")
                        isOtpSend.postValue("Done")
                    }
            }

            // Called when verification is failed add log statement to see the exception
            override fun onVerificationFailed(e: FirebaseException) {
                isOtpSend.postValue("Done")
            }

            // On code is sent by the firebase this method is called
            // in here we start a new activity where user can enter the OTP
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                resendToken = token
                isOtpSend.postValue("Done")
            }
        }


    fun sendOtp(phoneNumber: String, context: Context) {
        val number = "+91$phoneNumber"
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(context as Activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOtp(otp: String) {
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
            storedVerificationId.toString(), otp
        )
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    firebaseUser=it.result?.user!!
                    isVerificationDone.postValue("Done")
                } else {
                    isVerificationDone.postValue("Failed")
                }
            }.addOnFailureListener {
                isVerificationDone.postValue("Failed")
            }
    }

    fun loginUser(userPhoneNumber:String, userAuthId:String)=viewModelScope.launch {
        safeLoginUserCall(userPhoneNumber,userAuthId)
    }

    suspend fun safeLoginUserCall(userPhoneNumber:String, userAuthId:String){
        userData.postValue(Resource.Loading())
        try{
            val response=userRepository.loginUser(loginUserRequest(userAuthId,userPhoneNumber))
            Log.d(Constants.TAG, "safeLoginUserCall: ${response.body().toString()}")
            userData.postValue(handleLoginResponse(response))
        }catch (t:Throwable){
            when(t){
                is IOException -> userData.postValue(Resource.Error("Network Failure"))
                else -> userData.postValue(Resource.Error("Conversion Error"))
            }
        }

    }

    private fun handleLoginResponse(response: Response<LoginUserResponse>) : Resource<LoginUserResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(resultResponse.userStatus=="user_available"){
                    return Resource.Success( resultResponse)
                }else{
                    return Resource.Error("User Not Available , Sign Up First")
                }

            }
        }
        return Resource.Error(response.message())
    }

    fun createUser(signUpUserRequest: SignUpUserRequest) =viewModelScope.launch {
        safeCreateUserCall(signUpUserRequest)
    }

    suspend fun safeCreateUserCall(signUpUserRequest:SignUpUserRequest){
        signUpResponse.postValue(Resource.Loading())
        try{
            val response=userRepository.createUser(signUpUserRequest)
            Log.d(Constants.TAG, "safeLoginUserCall: ${response.body().toString()}")
            signUpResponse.postValue(handleSignUpResponse(response))
        }catch (t:Throwable){
            when(t){
                is IOException -> signUpResponse.postValue(Resource.Error("Network Failure"))
                else -> signUpResponse.postValue(Resource.Error("Conversion Error"))
            }
        }

    }

    private fun handleSignUpResponse(response: Response<SignUpResponse>) : Resource<SignUpResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(resultResponse.status=="user_already_available"){
                    return Resource.Error("With This Number user already available,Login Now")
                }else{
                    return Resource.Success(resultResponse)
                }

            }
        }
        return Resource.Error(response.message())
    }
//

}
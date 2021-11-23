package com.digitalgenius.bookmytable.ui.PhoneAuth_A;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.digitalgenius.bookmytable.api.models.entities.UserData;
import com.digitalgenius.bookmytable.databinding.ActivityPhoneAuthBinding;
import com.digitalgenius.bookmytable.repository.UserRepository;
import com.digitalgenius.bookmytable.ui.Home_A.HomeActivity;
import com.digitalgenius.bookmytable.ui.SignUp_A.SignUpActivity;
import com.digitalgenius.bookmytable.utils.Constants;
import com.digitalgenius.bookmytable.utils.Functions;
import com.digitalgenius.bookmytable.utils.Resource;
import com.digitalgenius.bookmytable.utils.SharedPrefManager;

public class PhoneAuthActivity extends AppCompatActivity {
    ActivityPhoneAuthBinding binding;
    private PhoneAuthViewModel phoneAuthViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PhoneAuthViewModelProviderFactory factory = new PhoneAuthViewModelProviderFactory(getApplication(),new UserRepository());
        phoneAuthViewModel = new ViewModelProvider(this, factory).get(PhoneAuthViewModel.class);

        setListener();
    }

    private void setListener() {
        binding.btnSendOtp.setOnClickListener(v -> {
            Functions.INSTANCE.show_progress_dialog(PhoneAuthActivity.this, "Send Otp");
            send_otp();
        });
        binding.btnVerifyOtp.setOnClickListener(v -> {
            Functions.INSTANCE.show_progress_dialog(PhoneAuthActivity.this, "Verify Otp");
            verify_otp();
        });

        phoneAuthViewModel.isOtpSend().observe(this, s -> {
            switch (s) {
                case "Done": {
                    Functions.INSTANCE.hide_progress_dialog();
                    binding.sendOtpLayout.setVisibility(View.GONE);
                    binding.verifyLayout.setVisibility(View.VISIBLE);
                    break;
                }
                case "Failed": {
                    Functions.INSTANCE.hide_progress_dialog();
                    Functions.INSTANCE.show_long_toast(PhoneAuthActivity.this, "Something Went Wrong");
                    break;
                }
                case "Not Called": {
                    Functions.INSTANCE.hide_progress_dialog();
                    binding.sendOtpLayout.setVisibility(View.VISIBLE);
                    binding.verifyLayout.setVisibility(View.GONE);
                    break;
                }

            }
        });


        phoneAuthViewModel.isVerificationDone().observe(this, (s) -> {
            switch (s) {
                case "Done": {
                    Functions.INSTANCE.hide_progress_dialog();
                    if (getIntent().getStringExtra("Type").equals("Login")) {
                        Functions.INSTANCE.show_progress_dialog(PhoneAuthActivity.this,"Login...");
                        Log.d(Constants.TAG, "setListener: "+binding.etPhoneNumber.getText().toString());
                        Log.d(Constants.TAG, "setListener: "+phoneAuthViewModel.firebaseUser.getUid());
                        phoneAuthViewModel.loginUser("" + binding.etPhoneNumber.getText().toString(),
                                "" + phoneAuthViewModel.firebaseUser.getUid());
                    } else {
                        goToSignUpActivity();
                    }
                    break;
                }
                case "Failed": {
                    Functions.INSTANCE.hide_progress_dialog();
                    Functions.INSTANCE.show_long_toast(PhoneAuthActivity.this, "Something Went Wrong");
                    break;
                }
                case "Not Called": {
                    Functions.INSTANCE.hide_progress_dialog();
                    binding.sendOtpLayout.setVisibility(View.VISIBLE);
                    binding.verifyLayout.setVisibility(View.GONE);
                    break;
                }
            }
        });


        phoneAuthViewModel.getUserData().observe(this, response -> {

            if(response instanceof Resource.Success){
                Functions.INSTANCE.hide_progress_dialog();
                UserData data=response.getData().getUserData();
                Constants.Companion.setUserData(data);
                saveDataInPref(data);
            }else if(response instanceof Resource.Error){
                Functions.INSTANCE.hide_progress_dialog();
                Functions.INSTANCE.show_long_toast(PhoneAuthActivity.this,response.getMessage());
            }
        });
    }


    private void saveDataInPref(UserData data) {
        SharedPrefManager sharedPrefManager =
                SharedPrefManager.getInstance(getApplicationContext());
        sharedPrefManager.setStringData("Login","True");
        sharedPrefManager.setUserData(data);
        goToHomeActivity();
    }

    private void goToHomeActivity() {
        startActivity(new Intent(PhoneAuthActivity.this, HomeActivity.class));
        finish();
    }

    private void goToSignUpActivity() {
        Intent intent=new Intent(PhoneAuthActivity.this, SignUpActivity.class);
        intent.putExtra("user_phone_number",binding.etPhoneNumber.getText().toString());
        intent.putExtra("user_auth_id",phoneAuthViewModel.firebaseUser.getUid());
        startActivity(intent);
        finish();
    }


    private void verify_otp() {

        if (binding.etOtp.getText().toString().length() != 6) {
            Toast.makeText(PhoneAuthActivity.this, "Enter the 6 digit OTP", Toast.LENGTH_SHORT).show();
        } else {
            phoneAuthViewModel.verifyOtp(binding.etOtp.getText().toString());
        }

    }


    @SuppressLint("SetTextI18n")
    private void send_otp() {

        if (binding.etPhoneNumber.getText().toString().length() != 10) {
            Toast.makeText(PhoneAuthActivity.this, "Enter the correct 10 digit Number", Toast.LENGTH_SHORT).show();
        } else {

            phoneAuthViewModel.sendOtp(binding.etPhoneNumber.getText().toString(), this);

        }

    }
}
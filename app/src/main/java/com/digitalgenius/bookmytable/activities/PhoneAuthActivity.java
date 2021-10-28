package com.digitalgenius.bookmytable.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.digitalgenius.bookmytable.databinding.ActivityPhoneAuthBinding;
import com.digitalgenius.bookmytable.utils.SharedPrefManager;

public class PhoneAuthActivity extends AppCompatActivity {
    ActivityPhoneAuthBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnNext.setOnClickListener(v -> {
            if (binding.btnNext.getText().toString().equals("VERIFY")) {
                verify_otp();
            } else {
                send_otp();
            }

        });

    }


    private void verify_otp() {

        if (binding.etOtp.getText().toString().length() != 6) {
            Toast.makeText(PhoneAuthActivity.this, "Enter the 6 digit OTP", Toast.LENGTH_SHORT).show();
        } else {
            //Process by Firebase
            saveLoginDetails();
            goToSignUpActivity();
        }

    }

    private void saveLoginDetails() {
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());
        sharedPrefManager.setStringData("Login", "True");
    }

    private void goToSignUpActivity() {
        //Check user details already filled up or not
        startActivity(new Intent(PhoneAuthActivity.this, SignActivity.class));
        finish();
    }

    @SuppressLint("SetTextI18n")
    private void send_otp() {

        if (binding.etPhoneNumber.getText().toString().length() != 10) {
            Toast.makeText(PhoneAuthActivity.this, "Enter the correct 10 digit Number", Toast.LENGTH_SHORT).show();
        } else {
            //Process by Firebase
            binding.layoutOtp.setVisibility(View.VISIBLE);
            binding.btnNext.setText("VERIFY");
        }

    }
}
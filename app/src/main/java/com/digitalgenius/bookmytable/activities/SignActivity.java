package com.digitalgenius.bookmytable.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.digitalgenius.bookmytable.databinding.ActivitySignUpBinding;

public class SignActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSignUp.setOnClickListener(v -> {
            if (checkFrom()) {
                call_sign_up_api();
            }
        });


    }

    private void call_sign_up_api() {
        //Calling APi

        goToHomeActivity();
    }

    private void goToHomeActivity() {
        startActivity(new Intent(SignActivity.this, HomeActivity.class));
        finish();
    }

    private boolean checkFrom() {

        if (binding.etEmail.getText().toString().isEmpty()) {
            Toast.makeText(SignActivity.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!binding.etEmail.getText().toString().matches(emailPattern)) {
            Toast.makeText(SignActivity.this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etUsername.getText().toString().isEmpty()) {
            Toast.makeText(SignActivity.this, "Enter Username", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etLocation.getText().toString().isEmpty()) {
            Toast.makeText(SignActivity.this, "Enter Location", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
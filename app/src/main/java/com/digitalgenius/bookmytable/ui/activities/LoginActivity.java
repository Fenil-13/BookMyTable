package com.digitalgenius.bookmytable.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.digitalgenius.bookmytable.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListener();
    }

    private void setListener() {
        Intent intent=new Intent(LoginActivity.this,PhoneAuthActivity.class);

        binding.btnLogin.setOnClickListener((v)->{
            intent.putExtra("Type","Login");
            startActivity(intent);
        });


        binding.btnSignUp.setOnClickListener((v)->{
            intent.putExtra("Type","SingUp");
            startActivity(intent);
        });
    }
}
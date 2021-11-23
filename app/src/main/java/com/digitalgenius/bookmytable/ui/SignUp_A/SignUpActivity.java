package com.digitalgenius.bookmytable.ui.SignUp_A;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.digitalgenius.bookmytable.api.models.entities.UserData;
import com.digitalgenius.bookmytable.api.models.requests.SignUpUserRequest;
import com.digitalgenius.bookmytable.databinding.ActivitySignUpBinding;
import com.digitalgenius.bookmytable.repository.UserRepository;
import com.digitalgenius.bookmytable.ui.Home_A.HomeActivity;
import com.digitalgenius.bookmytable.ui.PhoneAuth_A.PhoneAuthViewModel;
import com.digitalgenius.bookmytable.ui.PhoneAuth_A.PhoneAuthViewModelProviderFactory;
import com.digitalgenius.bookmytable.utils.Constants;
import com.digitalgenius.bookmytable.utils.Functions;
import com.digitalgenius.bookmytable.utils.Resource;
import com.digitalgenius.bookmytable.utils.SharedPrefManager;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    PhoneAuthViewModel phoneAuthViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        PhoneAuthViewModelProviderFactory factory = new PhoneAuthViewModelProviderFactory(getApplication(),new UserRepository());
        phoneAuthViewModel = new ViewModelProvider(this, factory).get(PhoneAuthViewModel.class);

        setListener();
    }

    private void setListener() {
        binding.btnCreateAccount.setOnClickListener(v -> {
            if (checkFrom()) {
                call_sign_up_api();
            }
        });

        phoneAuthViewModel.getSignUpResponse().observe(this,(response)->{
            if(response instanceof Resource.Success){
                Functions.INSTANCE.hide_progress_dialog();
                UserData data=new UserData(
                        getIntent().getStringExtra("user_auth_id"),
                        Constants.deviceToken,
                        binding.etEmail.getText().toString(),
                        response.getData().getId(),
                        binding.etLocation.getText().toString(),
                        binding.etUsername.getText().toString(),
                        getIntent().getStringExtra("user_phone_number"),
                        ""
                );
                Constants.Companion.setUserData(data);
                saveDataInPref(data);
            }else if(response instanceof Resource.Error){
                Functions.INSTANCE.hide_progress_dialog();
                Functions.INSTANCE.show_long_toast(SignUpActivity.this,response.getMessage());
            }else{

            }
        });
    }

    private void call_sign_up_api() {
        //Calling APi
        SignUpUserRequest signUpUserRequest=new SignUpUserRequest(
                getIntent().getStringExtra("user_auth_id"),
                Constants.deviceToken,
                binding.etEmail.getText().toString(),
                binding.etLocation.getText().toString(),
                binding.etUsername.getText().toString(),
                getIntent().getStringExtra("user_phone_number")
        );
        Functions.INSTANCE.show_progress_dialog(SignUpActivity.this,"Creating User...");
       phoneAuthViewModel.createUser(signUpUserRequest);
    }


    private void saveDataInPref(UserData data) {
        SharedPrefManager sharedPrefManager =
                SharedPrefManager.getInstance(getApplicationContext());
        sharedPrefManager.setStringData("Login","True");
        sharedPrefManager.setUserData(data);
        goToHomeActivity();
    }

    private void goToHomeActivity() {
        startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
        finish();
    }

    private boolean checkFrom() {

        if (binding.etEmail.getText().toString().isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!binding.etEmail.getText().toString().matches(emailPattern)) {
            Toast.makeText(SignUpActivity.this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etUsername.getText().toString().isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Enter Username", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etLocation.getText().toString().isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Enter Location", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
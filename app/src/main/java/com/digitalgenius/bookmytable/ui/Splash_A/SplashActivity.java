package com.digitalgenius.bookmytable.ui.Splash_A;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.ui.Home_A.HomeActivity;
import com.digitalgenius.bookmytable.ui.Intro_A.IntroActivity;
import com.digitalgenius.bookmytable.utils.Constants;
import com.digitalgenius.bookmytable.utils.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(getMainLooper()).postDelayed(
                () -> {
                    SharedPrefManager sharedPrefManager =SharedPrefManager.getInstance(getApplicationContext());

                    if(sharedPrefManager.getStringData("Login").equals("True")){
                        Constants.Companion.setUserData(sharedPrefManager.getUserData());
                        goToHomeActivity();
                    }else{
                        goToIntroActivity();
                    }
                },2000
        );
    }

    private void goToIntroActivity() {
        startActivity(new Intent(SplashActivity.this, IntroActivity.class));
        finish();
    }

    private void goToHomeActivity() {
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
    }

}
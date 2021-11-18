package com.digitalgenius.bookmytable.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.digitalgenius.bookmytable.databinding.ActivityRestaurantDetailsBinding;

public class RestaurantDetailsActivity extends AppCompatActivity {
    ActivityRestaurantDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBookTable.setOnClickListener(v -> {
            startActivity(new Intent(RestaurantDetailsActivity.this, TableBookingActivity.class));
        });
    }
}
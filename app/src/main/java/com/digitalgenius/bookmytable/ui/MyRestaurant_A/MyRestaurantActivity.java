package com.digitalgenius.bookmytable.ui.MyRestaurant_A;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.os.Bundle;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.databinding.ActivityMyRestaurantBinding;
import com.digitalgenius.bookmytable.repository.RestaurantRepository;

public class MyRestaurantActivity extends AppCompatActivity {
    ActivityMyRestaurantBinding binding;
    public MyRestaurantViewModel myRestaurantViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyRestaurantViewModelProviderFactory factory=new MyRestaurantViewModelProviderFactory(
                getApplication(),new RestaurantRepository()
        );
        myRestaurantViewModel=new ViewModelProvider(this,factory).get(MyRestaurantViewModel.class);

        binding=ActivityMyRestaurantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_my_restaurant);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navigateToCreateRestaurant();

    }

    public void navigateToCreateRestaurant(){
       Navigation.findNavController(this, R.id.nav_host_fragment_activity_my_restaurant)
               .navigate(R.id.action_restaurantTableFragment_to_createRestaurantFragment);
    }
}
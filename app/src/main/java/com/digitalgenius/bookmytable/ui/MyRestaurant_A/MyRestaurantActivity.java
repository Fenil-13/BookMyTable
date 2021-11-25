package com.digitalgenius.bookmytable.ui.MyRestaurant_A;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.databinding.ActivityMyRestaurantBinding;
import com.digitalgenius.bookmytable.repository.RestaurantRepository;
import com.digitalgenius.bookmytable.utils.Functions;
import com.digitalgenius.bookmytable.utils.Resource;

public class MyRestaurantActivity extends AppCompatActivity {
    ActivityMyRestaurantBinding binding;
    public MyRestaurantViewModel myRestaurantViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyRestaurantViewModelProviderFactory factory = new MyRestaurantViewModelProviderFactory(
                getApplication(), new RestaurantRepository()
        );
        myRestaurantViewModel = new ViewModelProvider(this, factory).get(MyRestaurantViewModel.class);

        binding = ActivityMyRestaurantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_my_restaurant);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        setListener();


    }

    private void setListener() {
        myRestaurantViewModel.getMyRestaurantResponse().observe(
                this,
                response -> {
                    if (response instanceof Resource.Success) {
                        Functions.INSTANCE.hide_progress_dialog();
                        if(response.getData().getRestaurantCount()==0){
                            Functions.INSTANCE.show_long_toast(MyRestaurantActivity.this,"Create Restaurant First");
                            navigateToCreateRestaurant();
                        }
                    } else if (response instanceof Resource.Error) {
                        Functions.INSTANCE.hide_progress_dialog();
                        Functions.INSTANCE.show_long_toast(MyRestaurantActivity.this, response.getMessage());
                    } else if (response instanceof Resource.Loading) {
                        Functions.INSTANCE.show_progress_dialog(MyRestaurantActivity.this, "Getting My Data");
                    }
                }
        );
    }

    public void navigateToCreateRestaurant() {
        Navigation.findNavController(this, R.id.nav_host_fragment_activity_my_restaurant)
                .navigate(R.id.action_restaurantTableFragment_to_createRestaurantFragment);
    }
}
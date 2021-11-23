package com.digitalgenius.bookmytable.ui.Home_A;

import android.os.Bundle;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.databinding.ActivityHomeBinding;
import com.digitalgenius.bookmytable.repository.RestaurantRepository;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    public RestaurantViewModel restaurantViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModels();
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home,R.id.navigation_profile)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void setViewModels() {
        RestaurantViewModelProviderFactory factory=
                new RestaurantViewModelProviderFactory(getApplication(),
                        new RestaurantRepository());

        restaurantViewModel= new ViewModelProvider(this,factory).get(RestaurantViewModel.class);
    }

}
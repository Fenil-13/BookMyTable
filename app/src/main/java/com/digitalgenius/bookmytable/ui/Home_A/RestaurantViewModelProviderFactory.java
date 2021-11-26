package com.digitalgenius.bookmytable.ui.Home_A;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.digitalgenius.bookmytable.repository.RestaurantRepository;
import com.digitalgenius.bookmytable.repository.UserRepository;

public class RestaurantViewModelProviderFactory implements ViewModelProvider.Factory {

    private Application app;
    private RestaurantRepository restaurantRepository;
    private UserRepository userRepository;

    public RestaurantViewModelProviderFactory(Application app, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.app = app;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return (T) new RestaurantViewModel(app, restaurantRepository, userRepository);
    }
}

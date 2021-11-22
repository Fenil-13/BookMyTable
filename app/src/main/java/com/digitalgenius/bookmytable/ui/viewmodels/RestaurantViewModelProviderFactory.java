package com.digitalgenius.bookmytable.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.digitalgenius.bookmytable.repository.RestaurantRepository;

public class RestaurantViewModelProviderFactory implements ViewModelProvider.Factory {

    private Application app;
    private RestaurantRepository restaurantRepository;

    public RestaurantViewModelProviderFactory(Application app, RestaurantRepository restaurantRepository) {
        this.app = app;
        this.restaurantRepository = restaurantRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return (T) new RestaurantViewModel(app,restaurantRepository);
    }
}

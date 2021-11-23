package com.digitalgenius.bookmytable.ui.TableBooking_A;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.digitalgenius.bookmytable.repository.RestaurantRepository;

public class BookingViewModelProviderFactory implements ViewModelProvider.Factory {

    private Application app;
    private RestaurantRepository restaurantRepository;

    public BookingViewModelProviderFactory(Application app, RestaurantRepository restaurantRepository) {
        this.app = app;
        this.restaurantRepository = restaurantRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return (T) new BookingViewModel(app,restaurantRepository);
    }
}

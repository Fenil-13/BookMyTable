package com.digitalgenius.bookmytable.ui.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.digitalgenius.bookmytable.repository.UserRepository;
import com.digitalgenius.bookmytable.ui.activities.PhoneAuthActivity;

public class PhoneAuthViewModelProviderFactory implements ViewModelProvider.Factory {

    private Application app;
    private UserRepository userRepository;

    public PhoneAuthViewModelProviderFactory(Application app, UserRepository userRepository) {
        this.app = app;
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return (T) new PhoneAuthViewModel(app,userRepository);
    }
}

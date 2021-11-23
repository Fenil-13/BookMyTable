package com.digitalgenius.bookmytable.ui.PhoneAuth_A;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.digitalgenius.bookmytable.repository.UserRepository;

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

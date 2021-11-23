package com.digitalgenius.bookmytable.ui.MyRestaurant_A.RestaurantProfile_F;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.databinding.FragmentProfileBinding;
import com.digitalgenius.bookmytable.databinding.FragmentRestaurantProfileBinding;

public class RestaurantProfileFragment extends Fragment {
    FragmentRestaurantProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentRestaurantProfileBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }
}
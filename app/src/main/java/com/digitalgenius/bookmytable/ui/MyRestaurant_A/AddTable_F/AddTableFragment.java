package com.digitalgenius.bookmytable.ui.MyRestaurant_A.AddTable_F;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.digitalgenius.bookmytable.databinding.FragmentAddTableBinding;
import com.digitalgenius.bookmytable.databinding.FragmentRestaurantTableBinding;

public class AddTableFragment extends Fragment {
    FragmentAddTableBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAddTableBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }
}
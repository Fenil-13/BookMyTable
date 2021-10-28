package com.digitalgenius.bookmytable.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.databinding.FragmentBookingBinding;
import com.digitalgenius.bookmytable.databinding.FragmentHomeBinding;

public class BookingFragment extends Fragment {

    FragmentBookingBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
package com.digitalgenius.bookmytable.ui.Home_A.Profile_F;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.databinding.FragmentProfileBinding;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantActivity;
import com.digitalgenius.bookmytable.ui.Splash_A.SplashActivity;
import com.digitalgenius.bookmytable.utils.Constants;
import com.digitalgenius.bookmytable.utils.SharedPrefManager;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi();
        setListener();
    }

    private void initUi() {
        binding.tvUsername.setText(Constants.Companion.getUserData().getUserName());
        binding.tvUserPhoneNumber.setText(Constants.Companion.getUserData().getUserPhoneNumber());
        binding.tvUserLocation.setText(Constants.Companion.getUserData().getUserLocation());
        binding.tvUserEmail.setText(Constants.Companion.getUserData().getUserEmail());
        if(!Constants.Companion.getUserData().getUserProfilePic().equals("")){
            Glide.with(requireContext())
                    .load(Constants.Companion.getUserData().getUserProfilePic())
                    .into(binding.ivProfilePic);
        }
    }

    private void setListener() {
        binding.termLayout.setOnClickListener((v)->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getContext().getString(R.string.term_link)));
            startActivity(browserIntent);
        });

        binding.policyLayout.setOnClickListener((v)->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getContext().getString(R.string.policy_link)));
            startActivity(browserIntent);
        });

        binding.logoutLayout.setOnClickListener((v)->{
            logout_user();
        });

        binding.restaurantLayout.setOnClickListener((v)->{
            Intent restaurantIntent=new Intent(requireContext(), MyRestaurantActivity.class);
            startActivity(restaurantIntent);
        });

        binding.ivProfilePic.setOnClickListener((v)->{

        });
    }

    private void logout_user() {
        SharedPrefManager sharedPrefManager =
                SharedPrefManager.getInstance(getContext().getApplicationContext());
        sharedPrefManager.setStringData("Login","False");
        startActivity(new Intent(getContext(), SplashActivity.class));
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}
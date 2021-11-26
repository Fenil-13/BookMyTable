package com.digitalgenius.bookmytable.ui.Home_A.Profile_F;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.api.models.entities.UserData;
import com.digitalgenius.bookmytable.databinding.FragmentProfileBinding;
import com.digitalgenius.bookmytable.ui.Home_A.HomeActivity;
import com.digitalgenius.bookmytable.ui.Home_A.RestaurantViewModel;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantActivity;
import com.digitalgenius.bookmytable.ui.Splash_A.SplashActivity;
import com.digitalgenius.bookmytable.utils.Constants;
import com.digitalgenius.bookmytable.utils.Functions;
import com.digitalgenius.bookmytable.utils.Resource;
import com.digitalgenius.bookmytable.utils.SharedPrefManager;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    RestaurantViewModel viewModel;

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
        viewModel = ((HomeActivity) getActivity()).restaurantViewModel;
        setListener();
    }


    @Override
    public void onResume() {
        super.onResume();
        setData();
    }
    SharedPrefManager sharedPrefManager;
    public void setData() {
        binding.tvUsername.setText(Constants.Companion.getUserData().getUserName());
        binding.tvUserPhoneNumber.setText(Constants.Companion.getUserData().getUserPhoneNumber());
        binding.tvUserLocation.setText(Constants.Companion.getUserData().getUserLocation());
        binding.tvUserEmail.setText(Constants.Companion.getUserData().getUserEmail());
        if (!Constants.Companion.getUserData().getUserProfilePic().equals("")) {
            Glide.with(requireContext())
                    .load(Constants.Companion.getUserData().getUserProfilePic())
                    .into(binding.ivProfilePic);
        }


        sharedPrefManager = SharedPrefManager.getInstance(getActivity().getApplicationContext());
        if(sharedPrefManager.getStringData("Mode").equals("Dark")){
            binding.toggleMode.setChecked(true);
            binding.tvUiMode.setText("Enable Light Mode");
        }else{
            binding.toggleMode.setChecked(false);
            binding.tvUiMode.setText("Enable Dark Mode");
        }
    }

    private void setListener() {
        binding.tvEditUser.setOnClickListener((v) -> {
            NavHostFragment.findNavController(ProfileFragment.this).navigate(
                    R.id.action_navigation_profile_to_userUpdateProfileFragment
            );
        });

        binding.termLayout.setOnClickListener((v) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getContext().getString(R.string.term_link)));
            startActivity(browserIntent);
        });

        binding.policyLayout.setOnClickListener((v) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getContext().getString(R.string.policy_link)));
            startActivity(browserIntent);
        });

        binding.logoutLayout.setOnClickListener((v) -> {
            logout_user();
        });

        binding.restaurantLayout.setOnClickListener((v) -> {
            Intent restaurantIntent = new Intent(requireContext(), MyRestaurantActivity.class);
            startActivity(restaurantIntent);
        });

        binding.ivProfilePic.setOnClickListener((v) -> {

        });

        binding.toggleMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPrefManager = SharedPrefManager.getInstance(getActivity().getApplicationContext());
            if(isChecked){
                binding.toggleMode.setChecked(true);
                binding.tvUiMode.setText("Enable Light Mode");
                sharedPrefManager.setStringData("Mode","Dark");
            }else{
                binding.toggleMode.setChecked(false);
                binding.tvUiMode.setText("Enable Dark Mode");
                sharedPrefManager.setStringData("Mode","Light");
            }
            toggleMode();
        });


    }

    private void toggleMode() {
        sharedPrefManager = SharedPrefManager.getInstance(getActivity().getApplicationContext());
        if(sharedPrefManager.getStringData("Mode").equals("Dark")){
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
        }else{
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
        }
    }

    private void logout_user() {
        SharedPrefManager sharedPrefManager =
                SharedPrefManager.getInstance(getContext().getApplicationContext());
        sharedPrefManager.setStringData("Login", "False");
        startActivity(new Intent(getContext(), SplashActivity.class));
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
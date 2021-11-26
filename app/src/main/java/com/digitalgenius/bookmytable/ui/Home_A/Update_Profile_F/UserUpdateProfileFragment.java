package com.digitalgenius.bookmytable.ui.Home_A.Update_Profile_F;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.api.models.entities.User;
import com.digitalgenius.bookmytable.api.models.entities.UserData;
import com.digitalgenius.bookmytable.api.models.requests.UserBookingRequest;
import com.digitalgenius.bookmytable.api.models.requests.UserUpdateRequest;
import com.digitalgenius.bookmytable.databinding.FragmentProfileBinding;
import com.digitalgenius.bookmytable.databinding.FragmentUserUpdateProfileBinding;
import com.digitalgenius.bookmytable.ui.Home_A.HomeActivity;
import com.digitalgenius.bookmytable.ui.Home_A.RestaurantViewModel;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantActivity;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantViewModel;
import com.digitalgenius.bookmytable.ui.SignUp_A.SignUpActivity;
import com.digitalgenius.bookmytable.ui.Splash_A.SplashActivity;
import com.digitalgenius.bookmytable.utils.Constants;
import com.digitalgenius.bookmytable.utils.Functions;
import com.digitalgenius.bookmytable.utils.Resource;
import com.digitalgenius.bookmytable.utils.SharedPrefManager;

public class UserUpdateProfileFragment extends Fragment {

    FragmentUserUpdateProfileBinding binding;
    RestaurantViewModel viewModel;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserUpdateProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ((HomeActivity) getActivity()).restaurantViewModel;
        viewModel.getUserUpdateResponse().setValue(new Resource.Loading());
        viewModel.getFetchUserByIdResponse().setValue(new Resource.Loading());
        setData();
        setListener();
    }

    private void setData() {
        UserData data = Constants.Companion.getUserData();
        binding.etPhoneNumber.setText(data.getUserPhoneNumber());
        binding.etUserEmail.setText(data.getUserEmail());
        binding.etLocation.setText(data.getUserLocation());
        binding.etUsername.setText(data.getUserName());
    }

    private void setListener() {
        binding.btnUserUpdate.setOnClickListener((v) -> {
            UserData data = Constants.Companion.getUserData();
            viewModel.getFetchUserByIdResponse().setValue(new Resource.Loading());
            if (checkForm()) {
                UserUpdateRequest request = new UserUpdateRequest(
                        data.getUserAuthId(),
                        data.getUserDeviceToken(),
                        binding.etUserEmail.getText().toString(),
                        data.getUserId(),
                        binding.etLocation.getText().toString(),
                        binding.etUsername.getText().toString(),
                        binding.etPhoneNumber.getText().toString()
                );
                Functions.INSTANCE.show_progress_dialog(requireContext(), "Updating User..");
                viewModel.updateUser(request);
            }
        });

        viewModel.getUserUpdateResponse().observe(getViewLifecycleOwner(),
                response -> {
                    if (response instanceof Resource.Success) {
                        Functions.INSTANCE.hide_progress_dialog();

                        UserBookingRequest userBookingRequest = new UserBookingRequest(
                                Constants.Companion.getUserData().getUserId()
                        );
                        Functions.INSTANCE.show_progress_dialog(requireContext(), "Storing User Data");
                        viewModel.fetchUserById(userBookingRequest);

                    } else if (response instanceof Resource.Error) {
                        Functions.INSTANCE.hide_progress_dialog();
                        Functions.INSTANCE.show_long_toast(requireContext(), response.getMessage());
                    } else if (response instanceof Resource.Loading) {

                    }
                });

        viewModel.getFetchUserByIdResponse().observe(getViewLifecycleOwner(),
                response -> {
                    if (response instanceof Resource.Success) {
                        Functions.INSTANCE.hide_progress_dialog();

                        viewModel.getFetchUserByIdResponse().setValue(new Resource.Loading());
                        viewModel.getUserUpdateResponse().setValue(new Resource.Loading());

                        User user = response.getData().getUser();
                        UserData userData=new UserData(
                                user.getUserAuthId(),
                                user.getUserDeviceToken(),
                                user.getUserEmail(),
                                user.getUserId(),
                                user.getUserLocation(),
                                user.getUserName(),
                                user.getUserPhoneNumber(),
                                user.getUserProfilePic()
                        );
                        Constants.Companion.setUserData(userData);

                        SharedPrefManager.getInstance(getActivity().getApplicationContext())
                                .setUserData(userData);
                        requireActivity().getOnBackPressedDispatcher().onBackPressed();
                    } else if (response instanceof Resource.Error) {
                        Functions.INSTANCE.hide_progress_dialog();
                        Functions.INSTANCE.show_long_toast(requireContext(), response.getMessage());
                    } else if (response instanceof Resource.Loading) {

                    }
                });
    }

    private boolean checkForm() {
        if (binding.etUsername.getText().toString().equals("")) {
            Functions.INSTANCE.show_long_toast(requireContext(), "Enter the username");
            return false;
        } else if (binding.etUserEmail.getText().toString().equals("")) {
            Functions.INSTANCE.show_long_toast(requireContext(), "Enter the Email Address");
            return false;
        } else if (!binding.etUserEmail.getText().toString().matches(emailPattern)) {
            Functions.INSTANCE.show_long_toast(requireContext(), "Enter Valid Email Address");
            return false;
        } else if (binding.etPhoneNumber.getText().toString().isEmpty()) {
            Functions.INSTANCE.show_long_toast(requireContext(), "Enter Phone Number");
            return false;
        } else if (binding.etLocation.getText().toString().isEmpty()) {
            Functions.INSTANCE.show_long_toast(requireContext(), "Enter Phone Number");
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
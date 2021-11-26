package com.digitalgenius.bookmytable.ui.MyRestaurant_A.Create_Restaurant_A;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.digitalgenius.bookmytable.api.models.entities.Restaurant;
import com.digitalgenius.bookmytable.api.models.requests.CreateRestaurantRequest;
import com.digitalgenius.bookmytable.api.models.requests.UpdateRestaurantRequest;
import com.digitalgenius.bookmytable.api.models.requests.UserBookingRequest;
import com.digitalgenius.bookmytable.api.models.responses.GeneralResponse;
import com.digitalgenius.bookmytable.databinding.FragmentCreateRestaurantBinding;
import com.digitalgenius.bookmytable.databinding.FragmentRestaurantProfileBinding;
import com.digitalgenius.bookmytable.ui.Home_A.HomeActivity;
import com.digitalgenius.bookmytable.ui.Home_A.RestaurantDetails_F.RestaurantDetailsFragmentArgs;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantActivity;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantViewModel;
import com.digitalgenius.bookmytable.utils.Constants;
import com.digitalgenius.bookmytable.utils.Functions;
import com.digitalgenius.bookmytable.utils.Resource;

import java.util.Locale;

public class CreateRestaurantFragment extends Fragment {

    FragmentCreateRestaurantBinding binding;
    MyRestaurantViewModel viewModel;

    String type = "Create";
    CreateRestaurantFragmentArgs args;
    Restaurant restaurant;
    private boolean isUpdated=false;

    TimePickerDialog.OnTimeSetListener startTimePickerDialogListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            binding.tvStartTime.setText("" + hourOfDay + ":" + minute);
        }
    };

    TimePickerDialog.OnTimeSetListener endTimePickerDialogListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            binding.tvEndTime.setText("" + hourOfDay + ":" + minute);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateRestaurantBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ((MyRestaurantActivity) requireActivity()).myRestaurantViewModel;

        isUpdated=false;

        args = CreateRestaurantFragmentArgs.fromBundle(getArguments());
        if (args.getType() != null) {
            type = args.getType();
        }

        if (type.equals("Create")) {
            binding.btnCreateAccount.setText("Create Restaurant");
        } else {
            binding.btnCreateAccount.setText("Update Restaurant");
            setDataToUi();
        }
        viewModel.getUpdateRestaurantResponse().setValue(new Resource.Loading());
        setListener();
    }
    private void setDataToUi() {

        restaurant = args.getRestaurantData();
        binding.tvEndTime.setText(restaurant.getRestaurantClosingTime());
        binding.etRestaurantPhoneNumber.setText(restaurant.getRestaurantContactNumber());
        binding.etLocation.setText(restaurant.getRestaurantLocation());
        binding.etRestaurantLongDesc.setText(restaurant.getRestaurantLongDesc());
        binding.etRestaurantName.setText(restaurant.getRestaurantName());
        binding.tvStartTime.setText(restaurant.getRestaurantOpeningTime());
        binding.etRestaurantShortDesc.setText(restaurant.getRestaurantShortDesc());
    }

    private void setListener() {
        binding.btnCreateAccount.setOnClickListener((v) -> {
            isUpdated=false;
            if (checkForm()) {
                CreateRestaurantRequest request = new CreateRestaurantRequest(
                        binding.tvEndTime.getText().toString(),
                        binding.etRestaurantPhoneNumber.getText().toString(),
                        binding.etLocation.getText().toString(),
                        binding.etRestaurantLongDesc.getText().toString(),
                        binding.etRestaurantName.getText().toString(),
                        binding.tvStartTime.getText().toString(),
                        binding.etRestaurantShortDesc.getText().toString(),
                        Constants.Companion.getUserData().getUserId()
                );

                if (type.equals("Create")) {
                    Functions.INSTANCE.show_progress_dialog(requireContext(), "Creating Restaurant");
                    viewModel.createRestaurant(request);
                } else {
                    Functions.INSTANCE.show_progress_dialog(requireContext(), "Updating Restaurant");
                    UpdateRestaurantRequest request1 = new UpdateRestaurantRequest(
                            binding.tvEndTime.getText().toString(),
                            binding.etRestaurantPhoneNumber.getText().toString(),
                            restaurant.getId(),
                            binding.etLocation.getText().toString(),
                            binding.etRestaurantLongDesc.getText().toString(),
                            binding.etRestaurantName.getText().toString(),
                            binding.tvStartTime.getText().toString(),
                            binding.etRestaurantShortDesc.getText().toString()
                    );

                    viewModel.updateRestaurant(request1);
                }


            }
        });

        binding.tvStartTime.setOnClickListener((v) -> {
            showStartTimePickerDialog();
        });

        binding.tvEndTime.setOnClickListener((v) -> {
            showEndTimePickerDialog();
        });

        viewModel.getCreateRestaurantResponse().observe(getViewLifecycleOwner(), new Observer<Resource<GeneralResponse>>() {
            @Override
            public void onChanged(Resource<GeneralResponse> response) {
                if (response instanceof Resource.Success) {
                    Functions.INSTANCE.hide_progress_dialog();
                    Toast.makeText(requireContext(), "Created Restaurant Successfully", Toast.LENGTH_SHORT).show();
                    requireActivity().getOnBackPressedDispatcher().onBackPressed();
                } else if (response instanceof Resource.Error) {
                    Functions.INSTANCE.hide_progress_dialog();
                    Functions.INSTANCE.show_long_toast(requireContext(), response.getMessage());
                } else if (response instanceof Resource.Loading) {

                }
            }
        });

        viewModel.getUpdateRestaurantResponse().observe(getViewLifecycleOwner(), new Observer<Resource<GeneralResponse>>() {
            @Override
            public void onChanged(Resource<GeneralResponse> response) {
                if (response instanceof Resource.Success) {
                    Functions.INSTANCE.hide_progress_dialog();
                    Toast.makeText(requireContext(), "Updated Restaurant Successfully", Toast.LENGTH_SHORT).show();
                    UserBookingRequest userBookingRequest = new UserBookingRequest(Constants.Companion.getUserData().getUserId());
                    viewModel.getMyRestaurant(userBookingRequest);
                } else if (response instanceof Resource.Error) {
                    Functions.INSTANCE.hide_progress_dialog();
                    Functions.INSTANCE.show_long_toast(requireContext(), response.getMessage());
                } else if (response instanceof Resource.Loading) {

                }
            }
        });

    }

    private boolean checkForm() {
        if (binding.etRestaurantName.getText().toString().equals("")) {
            Toast.makeText(requireContext(), "Enter the name", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.etRestaurantShortDesc.getText().toString().equals("")) {
            Toast.makeText(requireContext(), "Enter the short desc", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.etRestaurantLongDesc.getText().toString().equals("")) {
            Toast.makeText(requireContext(), "Enter the long desc", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.etRestaurantPhoneNumber.getText().toString().equals("")) {
            Toast.makeText(requireContext(), "Enter the Phone Number", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.etLocation.getText().toString().equals("")) {
            Toast.makeText(requireContext(), "Enter the Phone Number", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.tvStartTime.getText().toString().equals("") || binding.tvStartTime.getText().toString().equals("Start Time")) {
            Toast.makeText(requireContext(), "Choose Start Time", Toast.LENGTH_LONG).show();
            return false;
        } else if (binding.tvEndTime.getText().toString().equals("") || binding.tvEndTime.getText().toString().equals("End Time")) {
            Toast.makeText(requireContext(), "Choose End Time", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void showEndTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                endTimePickerDialogListener,
                12, 10, true
        );
        timePickerDialog.show();
    }

    private void showStartTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                startTimePickerDialogListener,
                12, 10, true
        );
        timePickerDialog.show();
    }


}
package com.digitalgenius.bookmytable.ui.MyRestaurant_A.Create_Restaurant_A;

import static com.digitalgenius.bookmytable.ui.ChoosePicture_A.ChoosePictureActivity.restaurantPics;
import static com.digitalgenius.bookmytable.utils.Constants.BASE_URL;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.api.models.entities.Restaurant;
import com.digitalgenius.bookmytable.api.models.requests.CreateRestaurantRequest;
import com.digitalgenius.bookmytable.api.models.requests.UpdateRestaurantRequest;
import com.digitalgenius.bookmytable.api.models.requests.UserBookingRequest;
import com.digitalgenius.bookmytable.api.models.responses.GeneralResponse;
import com.digitalgenius.bookmytable.databinding.FragmentCreateRestaurantBinding;
import com.digitalgenius.bookmytable.databinding.FragmentRestaurantProfileBinding;
import com.digitalgenius.bookmytable.ui.ChoosePicture_A.ChoosePictureActivity;
import com.digitalgenius.bookmytable.ui.Home_A.HomeActivity;
import com.digitalgenius.bookmytable.ui.Home_A.RestaurantDetails_F.RestaurantDetailsFragmentArgs;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantActivity;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantViewModel;
import com.digitalgenius.bookmytable.utils.Constants;
import com.digitalgenius.bookmytable.utils.Functions;
import com.digitalgenius.bookmytable.utils.Resource;

import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreateRestaurantFragment extends Fragment {

    FragmentCreateRestaurantBinding binding;
    MyRestaurantViewModel viewModel;

    String type = "Create";
    Restaurant restaurant;
    private boolean isUpdated = false;


    private boolean isUpdatedPic = false;
    private boolean infoDialog = false;

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

        isUpdated = false;

        if (Constants.Companion.getRestaurantData() != null) {
            type = "Edit";
            restaurant = Constants.Companion.getRestaurantData();
        }


        if (type.equals("Create")) {
            binding.btnCreateAccount.setText("Create Restaurant");
        } else {
            binding.btnCreateAccount.setText("Update Restaurant");
            setDataToUi();
        }

        viewModel.getUpdateRestaurantResponse().setValue(new Resource.Loading());
        setListener();

        if (!infoDialog) {
            infoDialog = true;
            SweetAlertDialog dialog = new SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Important Notice")
                    .setContentText("Here one pic not allowed, if you want to edit then all pics need to changes.")
                    .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    })
                    .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            requireActivity().getOnBackPressedDispatcher().onBackPressed();
                        }
                    });
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    private void setEditPics() {
        String preFix = BASE_URL + "static/restaurant_profile_pic/" + restaurant.getId() + "_restaurant_profile_pic_";
        Log.d(Constants.TAG, "setEditPics: " + preFix);
        try {
            Log.d(Constants.TAG, "setEditPics: " + preFix + restaurant.getRestaurantPics().get(0));
            Glide.with(requireContext())
                    .load(preFix + restaurant.getRestaurantPics().get(0))
                    .into(binding.ivRestaurantPic1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Log.d(Constants.TAG, "setEditPics: " + preFix + restaurant.getRestaurantPics().get(1));
            Glide.with(requireContext())
                    .load(preFix + restaurant.getRestaurantPics().get(1))
                    .into(binding.ivRestaurantPic2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Log.d(Constants.TAG, "setEditPics: " + preFix + restaurant.getRestaurantPics().get(2));
            Glide.with(requireContext())
                    .load(preFix + restaurant.getRestaurantPics().get(2))
                    .into(binding.ivRestaurantPic3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Log.d(Constants.TAG, "setEditPics: " + preFix + restaurant.getRestaurantPics().get(3));
            Glide.with(requireContext())
                    .load(preFix + restaurant.getRestaurantPics().get(3))
                    .into(binding.ivRestaurantPic4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPics() {
        if (restaurantPics[0] != null) {
            Glide.with(requireContext())
                    .load(restaurantPics[0])
                    .into(binding.ivRestaurantPic1);
        }
        if (restaurantPics[1] != null) {
            Glide.with(requireContext())
                    .load(restaurantPics[1])
                    .into(binding.ivRestaurantPic2);
        }
        if (restaurantPics[2] != null) {
            Glide.with(requireContext())
                    .load(restaurantPics[2])
                    .into(binding.ivRestaurantPic3);

        }
        if (restaurantPics[3] != null) {
            Glide.with(requireContext())
                    .load(restaurantPics[3])
                    .into(binding.ivRestaurantPic4);

        }
    }

    private void setDataToUi() {
        if (!isUpdatedPic) {
            setEditPics();
        }
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
            isUpdated = false;
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

        viewModel.getCreateRestaurantResponse().observe(getViewLifecycleOwner(), response -> {
            if (response instanceof Resource.Success) {
                Functions.INSTANCE.hide_progress_dialog();
                call_upload_pics();
            } else if (response instanceof Resource.Error) {
                Functions.INSTANCE.hide_progress_dialog();
                Functions.INSTANCE.show_long_toast(requireContext(), response.getMessage());
            } else if (response instanceof Resource.Loading) {

            }
        });

        viewModel.getUpdateRestaurantResponse().observe(getViewLifecycleOwner(), response -> {
            if (response instanceof Resource.Success) {
                Functions.INSTANCE.hide_progress_dialog();
                if (isUpdatedPic) {
                    call_upload_pics();
                } else {
                    Toast.makeText(requireContext(), "Updated Restaurant Successfully", Toast.LENGTH_SHORT).show();
                    UserBookingRequest userBookingRequest = new UserBookingRequest(Constants.Companion.getUserData().getUserId());
                    viewModel.getMyRestaurant(userBookingRequest);
                }
            } else if (response instanceof Resource.Error) {
                Functions.INSTANCE.hide_progress_dialog();
                Functions.INSTANCE.show_long_toast(requireContext(), response.getMessage());
            } else if (response instanceof Resource.Loading) {

            }
        });
        viewModel.getUploadRestaurantPicResponse().observe(getViewLifecycleOwner(),
                response -> {
                    if (response instanceof Resource.Success) {
                        Functions.INSTANCE.hide_progress_dialog();
                        if (type.equals("Edit")) {
                            Toast.makeText(requireContext(), "Updated Restaurant Successfully", Toast.LENGTH_SHORT).show();
                            UserBookingRequest userBookingRequest = new UserBookingRequest(Constants.Companion.getUserData().getUserId());
                            viewModel.getMyRestaurant(userBookingRequest);
                        } else {
                            Toast.makeText(requireContext(), "Created Restaurant Successfully", Toast.LENGTH_SHORT).show();
                            requireActivity().getOnBackPressedDispatcher().onBackPressed();
                        }

                    } else if (response instanceof Resource.Error) {
                        Functions.INSTANCE.hide_progress_dialog();
                        Functions.INSTANCE.show_long_toast(requireContext(), response.getMessage());
                    } else if (response instanceof Resource.Loading) {

                    }
                });


        binding.ivRestaurantPic1.setOnClickListener((v) -> {
            if (!isUpdatedPic && type.equals("Edit")) {
                null_array();
                isUpdatedPic = true;
            }
            Intent intent = new Intent(requireContext(), ChoosePictureActivity.class);
            intent.putExtra("position", "0");
            startActivity(intent);
        });

        binding.ivRestaurantPic2.setOnClickListener((v) -> {
            if (!isUpdatedPic && type.equals("Edit")) {
                null_array();
                isUpdatedPic = true;
            }
            Intent intent = new Intent(requireContext(), ChoosePictureActivity.class);
            intent.putExtra("position", "1");
            startActivity(intent);
        });

        binding.ivRestaurantPic3.setOnClickListener((v) -> {
            if (!isUpdatedPic && type.equals("Edit")) {
                null_array();
                isUpdatedPic = true;
            }
            Intent intent = new Intent(requireContext(), ChoosePictureActivity.class);
            intent.putExtra("position", "2");
            startActivity(intent);
        });
        binding.ivRestaurantPic4.setOnClickListener((v) -> {
            if (!isUpdatedPic && type.equals("Edit")) {
                null_array();
                isUpdatedPic = true;
            }
            Intent intent = new Intent(requireContext(), ChoosePictureActivity.class);
            intent.putExtra("position", "3");
            startActivity(intent);
        });

    }

    public void null_array() {
        restaurantPics[0] = null;
        restaurantPics[1] = null;
        restaurantPics[2] = null;
        restaurantPics[3] = null;
        binding.ivRestaurantPic1.setImageDrawable(requireContext().getDrawable(R.drawable.bg_color_primary_inactive));
        binding.ivRestaurantPic2.setImageDrawable(requireContext().getDrawable(R.drawable.bg_color_primary_inactive));
        binding.ivRestaurantPic3.setImageDrawable(requireContext().getDrawable(R.drawable.bg_color_primary_inactive));
        binding.ivRestaurantPic4.setImageDrawable(requireContext().getDrawable(R.drawable.bg_color_primary_inactive));
    }


    private void call_upload_pics() {
        Functions.INSTANCE.show_progress_dialog(requireContext(), "Uploading Images...");
        String res_id;
        if(type.equals("Edit")){
            res_id=restaurant.getId();
        }else{
            res_id = viewModel.getCreateRestaurantResponse().getValue().getData().getRestaurant_id();
        }

       viewModel.uploadRestaurantPics(
                Functions.INSTANCE.createPartFromString(res_id),
                Functions.INSTANCE.createPartFromString("restaurant_profile_pic"),
                Functions.INSTANCE.prepareFilePart("pic1", restaurantPics[0]),
                Functions.INSTANCE.prepareFilePart("pic2", restaurantPics[1]),
                Functions.INSTANCE.prepareFilePart("pic3", restaurantPics[2]),
                Functions.INSTANCE.prepareFilePart("pic4", restaurantPics[3])
        );
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
        } else if (isUpdatedPic) {
            if (restaurantPics[0] == null || restaurantPics[1] == null || restaurantPics[2] == null || restaurantPics[3] == null) {
                Toast.makeText(requireContext(), "Choose Picture of Restaurant", Toast.LENGTH_LONG).show();
                return false;
            }
        } else if (type.equals("Create")) {
            if (restaurantPics[0] == null || restaurantPics[1] == null || restaurantPics[2] == null || restaurantPics[3] == null) {
                Toast.makeText(requireContext(), "Choose Proof of Restaurant (4 Pics)", Toast.LENGTH_LONG).show();
                return false;
            }
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

    @Override
    public void onResume() {
        super.onResume();
        setPics();
    }
}
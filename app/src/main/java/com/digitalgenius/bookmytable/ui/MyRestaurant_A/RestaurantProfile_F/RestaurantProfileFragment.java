package com.digitalgenius.bookmytable.ui.MyRestaurant_A.RestaurantProfile_F;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.api.models.entities.Restaurant;
import com.digitalgenius.bookmytable.api.models.responses.UserRestaurantsResponse;
import com.digitalgenius.bookmytable.databinding.FragmentProfileBinding;
import com.digitalgenius.bookmytable.databinding.FragmentRestaurantProfileBinding;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantActivity;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantViewModel;

public class RestaurantProfileFragment extends Fragment {
    FragmentRestaurantProfileBinding binding;
    MyRestaurantViewModel restaurantViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentRestaurantProfileBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restaurantViewModel = ((MyRestaurantActivity) getActivity()).myRestaurantViewModel;
        setData();
    }

    public void setData(){
        Restaurant restaurant=restaurantViewModel.getMyRestaurantResponse().getValue().getData().getRestaurantList().get(0);

        binding.tvRestaurantName.setText(restaurant.getRestaurantName());
        binding.tvRestaurantStatus.setText("Status : "+restaurant.getStatus());
        binding.tvRestaurantStartTime.setText(restaurant.getRestaurantOpeningTime());
        binding.tvRestaurantEndTime.setText(restaurant.getRestaurantClosingTime());
        binding.tvRestaurantLocation.setText(restaurant.getRestaurantLocation());
        binding.tvRestaurantShortDesc.setText(restaurant.getRestaurantShortDesc());
        binding.tvRestaurantLongDesc.setText(restaurant.getRestaurantLongDesc());
        binding.tvRestaurantNumber.setText(restaurant.getRestaurantContactNumber());


    }
}
package com.digitalgenius.bookmytable.ui.MyRestaurant_A.RestaurantProfile_F;

import static com.digitalgenius.bookmytable.utils.Constants.BASE_URL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.api.models.entities.Restaurant;
import com.digitalgenius.bookmytable.api.models.responses.UserRestaurantsResponse;
import com.digitalgenius.bookmytable.databinding.FragmentProfileBinding;
import com.digitalgenius.bookmytable.databinding.FragmentRestaurantProfileBinding;
import com.digitalgenius.bookmytable.ui.Home_A.Home_F.HomeFragmentDirections;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantActivity;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantViewModel;
import com.digitalgenius.bookmytable.utils.Constants;

public class RestaurantProfileFragment extends Fragment {
    FragmentRestaurantProfileBinding binding;
    MyRestaurantViewModel restaurantViewModel;
    Restaurant restaurant;
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
        setListener();
    }

    private void setListener() {
        binding.tvEditRestaurant.setOnClickListener((v)->{
            Constants.Companion.setRestaurantData(restaurant);
            NavHostFragment.findNavController(RestaurantProfileFragment.this)
                    .navigate(R.id.action_restaurantProfileFragment_to_createRestaurantFragment);

        });
    }

    public void setData(){

        restaurant = restaurantViewModel.getMyRestaurantResponse().getValue().getData().getRestaurantList().get(0);

        binding.tvRestaurantName.setText(restaurant.getRestaurantName());
        binding.tvRestaurantStatus.setText("Status : "+restaurant.getStatus());
        binding.tvRestaurantStartTime.setText(restaurant.getRestaurantOpeningTime());
        binding.tvRestaurantEndTime.setText(restaurant.getRestaurantClosingTime());
        binding.tvRestaurantLocation.setText(restaurant.getRestaurantLocation());
        binding.tvRestaurantShortDesc.setText(restaurant.getRestaurantShortDesc());
        binding.tvRestaurantLongDesc.setText(restaurant.getRestaurantLongDesc());
        binding.tvRestaurantNumber.setText(restaurant.getRestaurantContactNumber());
        try{
            String url = BASE_URL + "static/restaurant_profile_pic/" + restaurant.getId() + "_restaurant_profile_pic_" + restaurant.getRestaurantPics().get(0);
            Glide.with(requireContext()).load(url)
                    .into(binding.ivProfilePic);
        }catch (Exception e){
            Glide.with(requireContext()).load(requireContext().getDrawable(R.drawable.ic_person))
                    .into(binding.ivProfilePic);
        }

    }
}
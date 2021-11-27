package com.digitalgenius.bookmytable.ui.Home_A.RestaurantDetails_F;

import static com.digitalgenius.bookmytable.utils.Constants.BASE_URL;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavArgs;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.api.models.entities.Restaurant;
import com.digitalgenius.bookmytable.databinding.FragmentRestaurantDetailsBinding;
import com.digitalgenius.bookmytable.ui.TableBooking_A.TableBookingActivity;

public class RestaurantDetailsFragment extends Fragment {
    FragmentRestaurantDetailsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRestaurantDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RestaurantDetailsFragmentArgs args=RestaurantDetailsFragmentArgs.fromBundle(getArguments());


        Restaurant restaurant=args.getRestaurantData();

        setData(restaurant);

        binding.btnBookTable.setOnClickListener(v -> {
            Intent intent=new Intent(requireContext(), TableBookingActivity.class);
            intent.putExtra("restaurant_id",restaurant.getId());
            intent.putExtra("restaurant_name",restaurant.getRestaurantName());
            startActivity(intent);
        });
    }

    private void setData(Restaurant restaurant) {
        binding.tvRestaurantName.setText(restaurant.getRestaurantName());
        binding.tvRestaurantShortDesc.setText(restaurant.getRestaurantShortDesc());
        binding.tvUserPhoneNumber.setText(restaurant.getRestaurantContactNumber());
        binding.tvRestaurantStartTime.setText(restaurant.getRestaurantOpeningTime());
        binding.tvRestaurantEndTime.setText(restaurant.getRestaurantClosingTime());
        binding.tvRestaurantLongDesc.setText(restaurant.getRestaurantLongDesc());
        binding.tvUserLocation.setText(restaurant.getRestaurantLocation());
        binding.tvUserEmail.setText(restaurant.getUserEmail());
        binding.tvUsername.setText(restaurant.getUserName());

        try{
            String url = BASE_URL + "static/restaurant_profile_pic/" + restaurant.getId() + "_restaurant_profile_pic_" + restaurant.getRestaurantPics().get(0);
            Glide.with(requireContext()).load(url)
                    .into(binding.ivRestaurantPic);
        }catch (Exception e){
            Glide.with(requireContext()).load(requireContext().getDrawable(R.drawable.dummy))
                    .into(binding.ivRestaurantPic);
        }

    }
}
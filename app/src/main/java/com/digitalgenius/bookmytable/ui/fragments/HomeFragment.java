package com.digitalgenius.bookmytable.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalgenius.bookmytable.adapters.RestaurantAdapter;
import com.digitalgenius.bookmytable.databinding.FragmentHomeBinding;
import com.digitalgenius.bookmytable.interfaces.RestaurantClickListener;
import com.digitalgenius.bookmytable.ui.activities.RestaurantDetailsActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RestaurantAdapter restaurantAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        get_restaurant_data();

        setUpRecycleView();
    }

    private void setUpRecycleView() {
        restaurantAdapter=new RestaurantAdapter(getContext(), new RestaurantClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(getContext(), RestaurantDetailsActivity.class));
            }
        });
        binding.rvRestaurant.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        binding.rvRestaurant.setHasFixedSize(true);
        binding.rvRestaurant.setAdapter(restaurantAdapter);
    }

    private void get_restaurant_data() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
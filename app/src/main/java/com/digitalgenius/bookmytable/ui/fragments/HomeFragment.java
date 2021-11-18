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

import com.digitalgenius.api.models.entities.RestaurantData;
import com.digitalgenius.bookmytable.ui.activities.RestaurantDetailsActivity;
import com.digitalgenius.bookmytable.adapters.RestaurantAdapter;
import com.digitalgenius.bookmytable.interfaces.RestaurantClickListener;
import com.digitalgenius.bookmytable.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayList<RestaurantData> restaurantDataList;
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
        restaurantAdapter=new RestaurantAdapter(restaurantDataList, getContext(), new RestaurantClickListener() {
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
        restaurantDataList = new ArrayList<>();
        List<String> pics = new ArrayList<>();
        pics.add("https://images.unsplash.com/photo-1552566626-52f8b828add9?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8cmVzdGF1cmFudHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=80");
        RestaurantData restaurantData = new RestaurantData(
                "10:00 PM",
                "+91 7573056506",
                "23423423",
                "Surat",
                "Kamrej char rasta",
                "Cheez Anguri",
                "8:00 AM",
                pics,
                "Mast Cheez hai khane layak",
                null,
                "verified",
                "fdsfsd");


        for (int i = 0; i < 10; i++) {
            restaurantDataList.add(restaurantData);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
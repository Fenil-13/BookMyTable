package com.digitalgenius.bookmytable.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalgenius.bookmytable.adapters.RestaurantAdapter;
import com.digitalgenius.bookmytable.databinding.FragmentHomeBinding;
import com.digitalgenius.bookmytable.interfaces.RestaurantClickListener;
import com.digitalgenius.bookmytable.repository.RestaurantRepository;
import com.digitalgenius.bookmytable.ui.activities.HomeActivity;
import com.digitalgenius.bookmytable.ui.activities.RestaurantDetailsActivity;
import com.digitalgenius.bookmytable.ui.viewmodels.RestaurantViewModel;
import com.digitalgenius.bookmytable.ui.viewmodels.RestaurantViewModelProviderFactory;
import com.digitalgenius.bookmytable.utils.Functions;
import com.digitalgenius.bookmytable.utils.Resource;

import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RestaurantAdapter restaurantAdapter;
    private RestaurantViewModel restaurantViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restaurantViewModel= ((HomeActivity) requireActivity()).restaurantViewModel;
        setUpRecycleView();
        setListener();
    }

    private void setListener() {
        restaurantViewModel.getAllRestaurant().observe(getViewLifecycleOwner(), response -> {
            if(response instanceof Resource.Success){
                Functions.INSTANCE.hide_progress_dialog();
                restaurantAdapter.differ.submitList(response.getData().getRestaurantList());
            }else if(response instanceof Resource.Error){
                Functions.INSTANCE.hide_progress_dialog();
                Functions.INSTANCE.show_long_toast(requireContext(),response.getMessage());
            }else if(response instanceof Resource.Loading){
                Functions.INSTANCE.show_progress_dialog(requireContext(),"Getting Restaurant");
            }
        });
    }

    private void setUpRecycleView() {
        restaurantAdapter=new RestaurantAdapter(getContext(),
                position -> startActivity(new Intent(getContext(), RestaurantDetailsActivity.class))
        );
        binding.rvRestaurant.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        binding.rvRestaurant.setHasFixedSize(true);
        binding.rvRestaurant.setAdapter(restaurantAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.digitalgenius.bookmytable.ui.MyRestaurant_A.RestaurantTable_F;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.api.models.entities.Restaurant;
import com.digitalgenius.bookmytable.api.models.requests.OwnerGetRestaurantTableRequest;
import com.digitalgenius.bookmytable.api.models.responses.OwnerGetRestaurantTableResponse;
import com.digitalgenius.bookmytable.api.models.responses.TableResponse;
import com.digitalgenius.bookmytable.databinding.FragmentRestaurantProfileBinding;
import com.digitalgenius.bookmytable.databinding.FragmentRestaurantTableBinding;
import com.digitalgenius.bookmytable.interfaces.RestaurantClickListener;
import com.digitalgenius.bookmytable.ui.Home_A.Home_F.HomeFragmentDirections;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantActivity;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantViewModel;
import com.digitalgenius.bookmytable.utils.Functions;
import com.digitalgenius.bookmytable.utils.Resource;

public class RestaurantTableFragment extends Fragment {
    FragmentRestaurantTableBinding binding;
    MyRestaurantViewModel restaurantViewModel;
    RestaurantTableAdapter restaurantTableAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRestaurantTableBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restaurantViewModel = ((MyRestaurantActivity) getActivity()).myRestaurantViewModel;

        setRecycleView();
        setListener();


        restaurantViewModel.getMyRestaurantResponse().observe(
                getViewLifecycleOwner(),
                response -> {
                    if (response instanceof Resource.Success) {
                        Functions.INSTANCE.hide_progress_dialog();
                        if (response.getData().getRestaurantCount() != 0) {
                            OwnerGetRestaurantTableRequest request = new OwnerGetRestaurantTableRequest(
                                    restaurantViewModel.getMyRestaurantResponse().getValue().getData().getRestaurantList().get(0).getId()
                            );
                            restaurantViewModel.getOwnerGetRestaurantTables(request);
                            Functions.INSTANCE.show_progress_dialog(requireContext(), "Getting Tables");
                        }
                    } else if (response instanceof Resource.Error) {
                        Functions.INSTANCE.hide_progress_dialog();
                        Functions.INSTANCE.show_long_toast(requireContext(), response.getMessage());
                    }
                }
        );




    }

    private void setListener() {
        restaurantViewModel.getOwnerGetRestaurantTableResponse().observe(getViewLifecycleOwner(),
                response -> {
                    if (response instanceof Resource.Success) {
                        Functions.INSTANCE.hide_progress_dialog();
                        restaurantTableAdapter.differ.submitList(response.getData().getTableList());
                    } else if (response instanceof Resource.Error) {
                        Functions.INSTANCE.hide_progress_dialog();
                        Functions.INSTANCE.show_long_toast(requireContext(), response.getMessage());
                    } else if (response instanceof Resource.Loading) {

                    }
                });
        binding.btnAddTable.setOnClickListener((v) -> {
            NavHostFragment.findNavController(RestaurantTableFragment.this)
                    .navigate(R.id.action_restaurantTableFragment_to_addTableFragment);
        });
    }

    private void setRecycleView() {
        restaurantTableAdapter = new RestaurantTableAdapter(getContext(), new RestaurantClickListener() {
            @Override
            public void onClick(int position) {
                TableResponse tableResponse = (TableResponse) restaurantTableAdapter.differ.getCurrentList().get(position);
                RestaurantTableFragmentDirections.ActionRestaurantTableFragmentToRestaurantTableTimeSlotFragment direction = RestaurantTableFragmentDirections.actionRestaurantTableFragmentToRestaurantTableTimeSlotFragment(
                        tableResponse
                );

                NavHostFragment.findNavController(RestaurantTableFragment.this).navigate(direction);
            }
        });
        binding.rvOwnerRestaurantTables.setAdapter(restaurantTableAdapter);
        binding.rvOwnerRestaurantTables.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvOwnerRestaurantTables.setHasFixedSize(false);
    }
}
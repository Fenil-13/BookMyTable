package com.digitalgenius.bookmytable.ui.Home_A.Home_F;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalgenius.bookmytable.api.models.entities.Restaurant;
import com.digitalgenius.bookmytable.databinding.FragmentHomeBinding;
import com.digitalgenius.bookmytable.ui.Home_A.HomeActivity;
import com.digitalgenius.bookmytable.ui.Home_A.RestaurantViewModel;
import com.digitalgenius.bookmytable.utils.Functions;
import com.digitalgenius.bookmytable.utils.Resource;

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
        restaurantViewModel = ((HomeActivity) requireActivity()).restaurantViewModel;
        setUpRecycleView();
        setListener();
    }

    private void setListener() {
        restaurantViewModel.getAllRestaurant().observe(getViewLifecycleOwner(), response -> {
            if (response instanceof Resource.Success) {
                Functions.INSTANCE.hide_progress_dialog();
                restaurantAdapter.differ.submitList(response.getData().getRestaurantList());

                if(restaurantAdapter.differ.getCurrentList().size()==0){
                    binding.rvRestaurant.setVisibility(View.GONE);
                    binding.layoutNoRestaurant.setVisibility(View.VISIBLE);
                }else{
                    binding.rvRestaurant.setVisibility(View.VISIBLE);
                    binding.layoutNoRestaurant.setVisibility(View.GONE);
                }

            } else if (response instanceof Resource.Error) {
                Functions.INSTANCE.hide_progress_dialog();
                Functions.INSTANCE.show_long_toast(requireContext(), response.getMessage());
            } else if (response instanceof Resource.Loading) {
                Functions.INSTANCE.show_progress_dialog(requireContext(), "Getting Restaurant");
            }
        });
    }

    private void setUpRecycleView() {
        restaurantAdapter = new RestaurantAdapter(getContext(),
                position -> {
                    HomeFragmentDirections.ActionNavigationHomeToNavigationRestaurantDetails direction=HomeFragmentDirections.
                            actionNavigationHomeToNavigationRestaurantDetails(
                                    (Restaurant) restaurantAdapter.differ.getCurrentList().get(position)
                            );
                    NavHostFragment.findNavController(this)
                            .navigate(direction);
                }

        );
        binding.rvRestaurant.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvRestaurant.setHasFixedSize(true);
        binding.rvRestaurant.setAdapter(restaurantAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
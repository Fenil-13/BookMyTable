package com.digitalgenius.bookmytable.ui.MyRestaurant_A.RestaurantTableTimeSlot_F;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.digitalgenius.bookmytable.api.models.entities.Restaurant;
import com.digitalgenius.bookmytable.api.models.entities.TimeSlot;
import com.digitalgenius.bookmytable.api.models.requests.OwnerGetRestaurantTableRequest;
import com.digitalgenius.bookmytable.api.models.responses.TableResponse;
import com.digitalgenius.bookmytable.databinding.FragmentRestaurantTableBinding;
import com.digitalgenius.bookmytable.databinding.FragmentRestaurantTableTimeSlotBinding;
import com.digitalgenius.bookmytable.interfaces.AvailableTableClickListener;
import com.digitalgenius.bookmytable.interfaces.RestaurantClickListener;
import com.digitalgenius.bookmytable.ui.Home_A.RestaurantDetails_F.RestaurantDetailsFragmentArgs;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantActivity;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantViewModel;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.RestaurantTable_F.RestaurantTableAdapter;
import com.digitalgenius.bookmytable.ui.TableBooking_A.AvailableTableAdapter;
import com.digitalgenius.bookmytable.ui.TableBooking_A.TableBookingActivity;
import com.digitalgenius.bookmytable.utils.Functions;
import com.digitalgenius.bookmytable.utils.Resource;

import java.util.List;

public class RestaurantTableTimeSlotFragment extends Fragment {
    FragmentRestaurantTableTimeSlotBinding binding;
    MyRestaurantViewModel restaurantViewModel;
    AvailableTableAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRestaurantTableTimeSlotBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restaurantViewModel = ((MyRestaurantActivity) getActivity()).myRestaurantViewModel;

        setRecycleView();
        set_data();
    }

    private void set_data() {
        RestaurantTableTimeSlotFragmentArgs args=RestaurantTableTimeSlotFragmentArgs.fromBundle(getArguments());
        TableResponse tableResponse=args.getTimeSlotData();
        adapter.differ.submitList(tableResponse.getTimeSlot());
        if(adapter.differ.getCurrentList().size()==0){
            binding.layoutNoTable.setVisibility(View.VISIBLE);
            binding.rvAvailableTables.setVisibility(View.GONE);
        }else{
            binding.layoutNoTable.setVisibility(View.GONE);
            binding.rvAvailableTables.setVisibility(View.VISIBLE);
        }
    }


    private void setRecycleView() {
        adapter = new AvailableTableAdapter(requireContext(), new AvailableTableClickListener() {
            @Override
            public void onClick(int position) {

            }
        });
        binding.rvAvailableTables.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvAvailableTables.setAdapter(adapter);
        binding.rvAvailableTables.setHasFixedSize(true);
    }

}
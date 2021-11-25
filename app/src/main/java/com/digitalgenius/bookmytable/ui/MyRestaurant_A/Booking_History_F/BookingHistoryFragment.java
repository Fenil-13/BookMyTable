package com.digitalgenius.bookmytable.ui.MyRestaurant_A.Booking_History_F;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.digitalgenius.bookmytable.api.models.requests.OwnerGetRestaurantTableRequest;
import com.digitalgenius.bookmytable.api.models.responses.BookingResponse;
import com.digitalgenius.bookmytable.api.models.responses.RestaurantBookingHistoryResponse;
import com.digitalgenius.bookmytable.databinding.FragmentAddTableBinding;
import com.digitalgenius.bookmytable.databinding.FragmentBookingBinding;
import com.digitalgenius.bookmytable.databinding.FragmentRestaurantBookingHistoryBinding;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantActivity;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantViewModel;
import com.digitalgenius.bookmytable.utils.Constants;
import com.digitalgenius.bookmytable.utils.Functions;
import com.digitalgenius.bookmytable.utils.Resource;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryFragment extends Fragment {
    FragmentRestaurantBookingHistoryBinding binding;
    MyRestaurantViewModel restaurantViewModel;

    RestaurantBookingFragment inCompletedFragment;

    RestaurantBookingFragment completedFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRestaurantBookingHistoryBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restaurantViewModel = ((MyRestaurantActivity) getActivity()).myRestaurantViewModel;

        OwnerGetRestaurantTableRequest request = new OwnerGetRestaurantTableRequest(
                restaurantViewModel.getMyRestaurantResponse().getValue().getData().getRestaurantList().get(0).getId()
        );
        restaurantViewModel.getBookingListByRestaurantId(request);

        setListener();
    }

    List<BookingResponse> inCompletedHistory;
    List<BookingResponse> completedHistory;

    private void setListener() {
        restaurantViewModel.getRestaurantBookingHistory().observe(getViewLifecycleOwner(),
                response -> {
                    RestaurantBookingHistoryResponse historyResponse=response.getData();
                    if (response instanceof Resource.Success) {
                        Functions.INSTANCE.hide_progress_dialog();
                        Log.d(Constants.TAG, "setListener: "+historyResponse.getSuccess());

                        inCompletedHistory = historyResponse.getIncompleteBookingList();

                        completedHistory = historyResponse.getCompletedBookingList();

                        Log.d(Constants.TAG, "setListener: inCompletedHistory "+inCompletedHistory.size());
                        Log.d(Constants.TAG, "setListener: completedHistory "+completedHistory.size());
                        binding.viewPager.setAdapter(new ViewPagerAdapter(getActivity()));
                        new TabLayoutMediator(binding.tabs, binding.viewPager,
                                (tab, position) -> {
                                    if (position == 0) {
                                        tab.setText("Running");
                                    } else {
                                        tab.setText("Completed");
                                    }
                                }).attach();


                    } else if (response instanceof Resource.Error) {
                        Functions.INSTANCE.hide_progress_dialog();
                        Functions.INSTANCE.show_long_toast(requireContext(), response.getMessage());
                    } else if (response instanceof Resource.Loading) {
                        Functions.INSTANCE.show_progress_dialog(requireContext(),"Getting History");
                    }
                });
    }


    public class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            inCompletedFragment = new RestaurantBookingFragment("inCompleted",inCompletedHistory);
            completedFragment = new RestaurantBookingFragment("Completed",completedHistory);
            if (position == 0) {
                return inCompletedFragment;
            } else {
                return completedFragment;
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

}
package com.digitalgenius.bookmytable.ui.Home_A.Booking_F;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.api.models.entities.Booking;
import com.digitalgenius.bookmytable.api.models.requests.BookTableRequest;
import com.digitalgenius.bookmytable.api.models.requests.UserBookingRequest;
import com.digitalgenius.bookmytable.databinding.FragmentBookingBinding;
import com.digitalgenius.bookmytable.interfaces.BookingDialogListener;
import com.digitalgenius.bookmytable.interfaces.RestaurantClickListener;
import com.digitalgenius.bookmytable.ui.Home_A.HomeActivity;
import com.digitalgenius.bookmytable.ui.Home_A.RestaurantViewModel;
import com.digitalgenius.bookmytable.ui.TableBooking_A.DialogBookingSuccessFragment;
import com.digitalgenius.bookmytable.utils.Functions;
import com.digitalgenius.bookmytable.utils.Resource;

public class BookingFragment extends Fragment {

    FragmentBookingBinding binding;
    BookingAdapter bookingAdapter;
    private RestaurantViewModel restaurantViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restaurantViewModel = ((HomeActivity) requireActivity()).restaurantViewModel;
        setUpBookingRv();
        setListener();
    }



    private void setListener() {
        restaurantViewModel.getUserBookingHistory().observe(getViewLifecycleOwner(), response -> {
            if (response instanceof Resource.Success) {
                Functions.INSTANCE.hide_progress_dialog();
                bookingAdapter.differ.submitList(response.getData().getBookingList());
                if(response.getData().getBookingCount()==0){
                    binding.rvBookingHistory.setVisibility(View.GONE);
                    binding.layoutNoHistory.setVisibility(View.VISIBLE);
                }else{
                    binding.rvBookingHistory.setVisibility(View.VISIBLE);
                    binding.layoutNoHistory.setVisibility(View.GONE);
                }
            } else if (response instanceof Resource.Error) {
                Functions.INSTANCE.hide_progress_dialog();
                Functions.INSTANCE.show_long_toast(requireContext(), response.getMessage());
            } else if (response instanceof Resource.Loading) {
                Functions.INSTANCE.show_progress_dialog(requireContext(), "Getting History");
            }
        });
    }


    private void openDetailDialog(Booking booking) {
        FragmentManager fragmentManager = getChildFragmentManager();
        BookTableRequest bookTableRequest=new BookTableRequest(
                booking.getRestaurantId(),
                booking.getRestaurantName(),
                booking.getTableId(),
                booking.getTableType(),
                booking.getTimeSlot(),
                booking.getUserId(),
                booking.getUserName(),
                booking.getBookingDate()
        );

        DialogBookingSuccessFragment newFragment = new DialogBookingSuccessFragment(new BookingDialogListener() {
            @Override
            public void onClick() {
            }
        },bookTableRequest);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.fragment_container_view,newFragment).addToBackStack(null).commit();
    }


    private void setUpBookingRv() {
        bookingAdapter = new BookingAdapter(getContext(), new RestaurantClickListener() {
            @Override
            public void onClick(int position) {
                openDetailDialog((Booking)bookingAdapter.differ.getCurrentList().get(position));
            }
        });
        binding.rvBookingHistory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvBookingHistory.setHasFixedSize(true);
        binding.rvBookingHistory.setAdapter(bookingAdapter);
    }

}
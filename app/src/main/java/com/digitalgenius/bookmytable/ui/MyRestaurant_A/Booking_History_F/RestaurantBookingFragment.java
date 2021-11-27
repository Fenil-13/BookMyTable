package com.digitalgenius.bookmytable.ui.MyRestaurant_A.Booking_History_F;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.api.models.entities.Booking;
import com.digitalgenius.bookmytable.api.models.requests.BookTableRequest;
import com.digitalgenius.bookmytable.api.models.requests.OrderCompletedRequest;
import com.digitalgenius.bookmytable.api.models.responses.BookingResponse;
import com.digitalgenius.bookmytable.databinding.FragmentRestaurantBookingBinding;
import com.digitalgenius.bookmytable.interfaces.BookingDialogListener;
import com.digitalgenius.bookmytable.interfaces.RestaurantClickListener;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantActivity;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantViewModel;
import com.digitalgenius.bookmytable.ui.TableBooking_A.DialogBookingSuccessFragment;
import com.digitalgenius.bookmytable.utils.Constants;
import com.digitalgenius.bookmytable.utils.Functions;
import com.digitalgenius.bookmytable.utils.Resource;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class RestaurantBookingFragment extends Fragment {
    FragmentRestaurantBookingBinding binding;
    MyRestaurantViewModel restaurantViewModel;
    RestaurantBookingAdapter bookingAdapter;
    String mode;
    List<BookingResponse> data;

    public RestaurantBookingFragment(String mode, List<BookingResponse> data) {
        this.mode = mode;
        this.data = data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRestaurantBookingBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restaurantViewModel=((MyRestaurantActivity)getActivity()).myRestaurantViewModel;
        setUpBookingRv();
        setData(data);
        setListener();
    }

    private void setListener() {
        restaurantViewModel.getOrderCompletedResponse().observe(getViewLifecycleOwner(),
                (response)->{
                    if (response instanceof Resource.Success) {
                        Functions.INSTANCE.hide_progress_dialog();
                        getActivity().onBackPressed();
                    } else if (response instanceof Resource.Error) {
                        Functions.INSTANCE.hide_progress_dialog();
                        Functions.INSTANCE.show_long_toast(requireContext(), response.getMessage());
                    } else if (response instanceof Resource.Loading) {

                    }
                });
    }


    public void setData(List<BookingResponse> historyData) {
        bookingAdapter.differ.submitList(historyData);
        if( bookingAdapter.differ.getCurrentList().size()==0){
            binding.rvBookingHistory.setVisibility(View.GONE);
            binding.layoutNoHistory.setVisibility(View.VISIBLE);
        }else{
            binding.rvBookingHistory.setVisibility(View.VISIBLE);
            binding.layoutNoHistory.setVisibility(View.GONE);
        }
    }

    private void setUpBookingRv() {
        bookingAdapter = new RestaurantBookingAdapter(getContext(), new RestaurantClickListener() {
            @Override
            public void onClick(int position) {
                //Delete API
                if(mode.equals("inCompleted")){
                    OrderCompletedRequest orderCompletedRequest=new
                            OrderCompletedRequest(((BookingResponse)bookingAdapter.differ.getCurrentList().get(position)).getId());
                    restaurantViewModel.orderCompleted(orderCompletedRequest);
                    Functions.INSTANCE.show_progress_dialog(requireContext(),"Marking as read completed");
                }else{
                    openDetailDialog((BookingResponse)bookingAdapter.differ.getCurrentList().get(position));
                }
            }
        }, mode);
        binding.rvBookingHistory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvBookingHistory.setHasFixedSize(true);
        binding.rvBookingHistory.setAdapter(bookingAdapter);
    }


    private void openDetailDialog(BookingResponse BookingResponse) {
        FragmentManager fragmentManager = getChildFragmentManager();
        BookTableRequest bookTableRequest=new BookTableRequest(
                BookingResponse.getRestaurantId(),
                BookingResponse.getRestaurantName(),
                BookingResponse.getTableId(),
                BookingResponse.getTableType(),
                BookingResponse.getTimeSlot(),
                BookingResponse.getUserId(),
                BookingResponse.getUserName(),
                BookingResponse.getBookingDate()
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
}
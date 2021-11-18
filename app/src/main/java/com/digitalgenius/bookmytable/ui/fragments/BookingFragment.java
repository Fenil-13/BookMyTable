package com.digitalgenius.bookmytable.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.adapters.BookingAdapter;
import com.digitalgenius.bookmytable.databinding.FragmentBookingBinding;
import com.digitalgenius.bookmytable.interfaces.BookingDetailsListener;
import com.digitalgenius.bookmytable.interfaces.BookingDialogListener;

public class BookingFragment extends Fragment {

    FragmentBookingBinding binding;
    BookingAdapter currentBookingAdapter;
    BookingAdapter completedBookingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        get_current_booking_data();

        setUpCurrentBookingRv();

        get_completed_booking_data();

        setUpCompletedBookingRv();
    }

    private void get_completed_booking_data() {
    }

    private void setUpCurrentBookingRv() {
        currentBookingAdapter = new BookingAdapter(getContext(), 2, new BookingDetailsListener() {
            @Override
            public void onClick(int position) {
                openDetailDialog();
            }
        });
        binding.rvCurrentBooking.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvCurrentBooking.setHasFixedSize(true);
        binding.rvCurrentBooking.setAdapter(currentBookingAdapter);
    }

    private void openDetailDialog() {
        FragmentManager fragmentManager = getChildFragmentManager();
        DialogBookingSuccessFragment newFragment = new DialogBookingSuccessFragment(new BookingDialogListener() {
            @Override
            public void onClick() {
            }
        }, getString(R.string.transaction_details));

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.fragment_container_view,newFragment).addToBackStack(null).commit();
    }


    private void setUpCompletedBookingRv() {
        completedBookingAdapter = new BookingAdapter(getContext(), 10, new BookingDetailsListener() {
            @Override
            public void onClick(int position) {
                openDetailDialog();
            }
        });
        binding.rvCompletedBooking.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvCompletedBooking.setHasFixedSize(true);
        binding.rvCompletedBooking.setAdapter(completedBookingAdapter);
    }

    private void get_current_booking_data() {

    }
}
package com.digitalgenius.bookmytable.ui.TableBooking_A;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.api.models.entities.TimeSlot;
import com.digitalgenius.bookmytable.api.models.requests.BookTableRequest;
import com.digitalgenius.bookmytable.api.models.requests.GetTableByTypeRequest;
import com.digitalgenius.bookmytable.databinding.ActivityTableBookingBinding;
import com.digitalgenius.bookmytable.interfaces.AvailableTableClickListener;
import com.digitalgenius.bookmytable.interfaces.BookingDialogListener;
import com.digitalgenius.bookmytable.repository.RestaurantRepository;
import com.digitalgenius.bookmytable.utils.Constants;
import com.digitalgenius.bookmytable.utils.Functions;
import com.digitalgenius.bookmytable.utils.Resource;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TableBookingActivity extends AppCompatActivity {
    ActivityTableBookingBinding binding;
    int selectedItem = 0;

    BookingViewModel bookingViewModel;
    AvailableTableAdapter adapter;

    String[] tableSizes = {"2", "3", "4", "5", "6", "7", "8"};
    int checkedItem = 0;

    BookTableRequest bookTableRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTableBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BookingViewModelProviderFactory factory = new BookingViewModelProviderFactory(getApplication(),
                new RestaurantRepository());
        bookingViewModel = new ViewModelProvider(this, factory).get(BookingViewModel.class);

        setListener();

        openTableSizeDialog();


        setRecycleView();
    }

    private void setRecycleView() {
        adapter = new AvailableTableAdapter(TableBookingActivity.this, new AvailableTableClickListener() {
            @Override
            public void onClick(int position) {
                openConfirmDialog(position);
            }
        });
        binding.rvAvailableTables.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvAvailableTables.setAdapter(adapter);
        binding.rvAvailableTables.setHasFixedSize(true);
    }

    private void openConfirmDialog(int position) {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Are you sure for this time slot?")
                .setContentText("" + ((TimeSlot) adapter.differ.getCurrentList().get(position)).getTime())
                .setConfirmText("Yes,Book It")
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    book_table(position);
                })
                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();

    }

    private void book_table(int position) {
        bookTableRequest = new BookTableRequest(
                getIntent().getStringExtra("restaurant_id"),
                getIntent().getStringExtra("restaurant_name"),
                bookingViewModel.getTableByType().getValue().getData().getTableId(),
                tableSizes[selectedItem],
                ((TimeSlot) adapter.differ.getCurrentList().get(position)).getTime(),
                Constants.Companion.getUserData().getUserId(),
                Constants.Companion.getUserData().getUserName(),
                android.text.format.DateFormat.format("dd-MM-yyyy", new Date()).toString()

        );
        bookingViewModel.book_table(bookTableRequest);
    }

    private void setListener() {
        bookingViewModel.getTableByType().observe(this, response -> {
            if (response instanceof Resource.Success) {
                Functions.INSTANCE.hide_progress_dialog();
                adapter.differ.submitList(response.getData().getTimeSlot());
                if(adapter.differ.getCurrentList().size()==0){
                    binding.layoutNoTable.setVisibility(View.VISIBLE);
                    binding.rvAvailableTables.setVisibility(View.GONE);
                }else{
                    binding.layoutNoTable.setVisibility(View.GONE);
                    binding.rvAvailableTables.setVisibility(View.VISIBLE);
                }
            } else if (response instanceof Resource.Error) {
                Functions.INSTANCE.hide_progress_dialog();

            } else if (response instanceof Resource.Loading) {

            }
        });

        bookingViewModel.getBookTableResponse().observe(this,response->{
            if (response instanceof Resource.Success) {
                Functions.INSTANCE.hide_progress_dialog();
                openSuccessDialog();
            } else if (response instanceof Resource.Error) {
                Functions.INSTANCE.hide_progress_dialog();

            } else if (response instanceof Resource.Loading) {

            }
        });
    }

    private void openSuccessDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogBookingSuccessFragment newFragment = new DialogBookingSuccessFragment(new BookingDialogListener() {
            @Override
            public void onClick() {
                onBackPressed();
                finish();
            }
        },bookTableRequest);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }


    private void openTableSizeDialog() {


        new MaterialAlertDialogBuilder(this)
                .setTitle("Choose Table Size")
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(TableBookingActivity.this, "Choose Table Type for go ahead", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                })
                .setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        GetTableByTypeRequest getTableByTypeRequest=
                                new GetTableByTypeRequest(getIntent().getStringExtra("restaurant_id"),
                                        ""+tableSizes[selectedItem]);
                        Functions.INSTANCE.show_progress_dialog(TableBookingActivity.this,
                                "Getting Available Table");
                        bookingViewModel.get_table_by_type(getTableByTypeRequest);
                    }
                })
                .setSingleChoiceItems(tableSizes, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedItem = which;
                    }
                })
                .show();
    }
}
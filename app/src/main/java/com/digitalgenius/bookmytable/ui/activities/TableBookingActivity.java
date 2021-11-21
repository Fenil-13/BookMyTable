package com.digitalgenius.bookmytable.ui.activities;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.databinding.ActivityTableBookingBinding;
import com.digitalgenius.bookmytable.interfaces.BookingDialogListener;
import com.digitalgenius.bookmytable.ui.fragments.DialogBookingSuccessFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

public class TableBookingActivity extends AppCompatActivity {
    ActivityTableBookingBinding binding;
    int selectedItem=0;
    int hour, minute;
    String am_pm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTableBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        openTableSizeDialog();

        binding.btnBookTable.setOnClickListener(v -> {
            openSuccessDialog();
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
        },getString(R.string.thank_you));

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    private void openTimeSlotDialog() {
        MaterialTimePicker picker=new MaterialTimePicker.Builder()
                .setTitleText("Select Time")
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .build();

        picker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TableBookingActivity.this, "Choose Time for go ahead", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = picker.getHour();
                    minute = picker.getMinute();
                }
                if(hour > 12) {
                    am_pm = "PM";
                    hour = hour - 12;
                }
                else
                {
                    am_pm="AM";
                }

                binding.bookingContainer.setVisibility(View.VISIBLE);
            }
        });
        picker.show(getSupportFragmentManager(),"timepicker");

    }

    private void openTableSizeDialog() {
        String[] tableSizes={"2","3","4","5","6","7","8"};
        int checkedItem=0;

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
                        openTimeSlotDialog();
                    }
                })
                .setSingleChoiceItems(tableSizes, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedItem=which;
                    }
                })
                .show();
    }
}
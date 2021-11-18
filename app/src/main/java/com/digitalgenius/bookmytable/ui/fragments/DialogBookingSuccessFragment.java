package com.digitalgenius.bookmytable.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.interfaces.BookingDialogListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DialogBookingSuccessFragment extends DialogFragment {

    private View root_view;
    private BookingDialogListener listener;
    private String title;

    public DialogBookingSuccessFragment(BookingDialogListener listener,String title) {
        this.listener = listener;
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.dialog_booking_success, container, false);
        ((TextView) root_view.findViewById(R.id.tvTitle)).setText(title);
        ((FloatingActionButton) root_view.findViewById(R.id.btnClose)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.onClick();
            }
        });

        return root_view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
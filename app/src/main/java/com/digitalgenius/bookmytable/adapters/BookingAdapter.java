package com.digitalgenius.bookmytable.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalgenius.bookmytable.databinding.ItemBookingBinding;
import com.digitalgenius.bookmytable.interfaces.BookingDetailsListener;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.bookingViewHolder> {

    private Context context;
    private ItemBookingBinding binding ;
    int count;
    private BookingDetailsListener listener ;

    public BookingAdapter(Context context, int count, BookingDetailsListener listener) {
        this.context = context;
        this.count = count;
        this.listener = listener;
    }

    @NonNull
    @Override
    public bookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding= ItemBookingBinding.inflate(LayoutInflater.from(context),parent,false);
        return new bookingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull bookingViewHolder holder, int position) {
        binding.tvDetails.setOnClickListener(v->{
            listener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class bookingViewHolder extends RecyclerView.ViewHolder {
        public bookingViewHolder(@NonNull ItemBookingBinding itemView) {
            super(itemView.getRoot());
        }
    }
}

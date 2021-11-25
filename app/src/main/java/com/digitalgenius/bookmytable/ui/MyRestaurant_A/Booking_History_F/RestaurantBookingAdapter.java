package com.digitalgenius.bookmytable.ui.MyRestaurant_A.Booking_History_F;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.api.models.entities.Booking;
import com.digitalgenius.bookmytable.api.models.responses.BookingResponse;
import com.digitalgenius.bookmytable.databinding.ItemBookingBinding;
import com.digitalgenius.bookmytable.interfaces.RestaurantClickListener;

import java.util.Date;

public class RestaurantBookingAdapter extends RecyclerView.Adapter<RestaurantBookingAdapter.bookingViewHolder> {

    private Context context;
    private ItemBookingBinding binding ;
    private RestaurantClickListener clickListener;
    String mode;
    private DiffUtil.ItemCallback<BookingResponse> differCallback=new DiffUtil.ItemCallback<BookingResponse>() {
        @Override
        public boolean areItemsTheSame(@NonNull BookingResponse oldItem, @NonNull BookingResponse newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull BookingResponse oldItem, @NonNull BookingResponse newItem) {
            return oldItem.equals(newItem);
        }
    };
    public AsyncListDiffer differ;



    public RestaurantBookingAdapter(Context context, RestaurantClickListener clickListener,String mode) {
        this.context = context;
        this.clickListener = clickListener;
        this.mode = mode;
        differ = new AsyncListDiffer(this, differCallback);
    }

    @NonNull
    @Override
    public bookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ItemBookingBinding.inflate(LayoutInflater.from(context),parent,false);
        return new bookingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull bookingViewHolder holder, int position) {
        BookingResponse booking= (BookingResponse) differ.getCurrentList().get(position);

        binding.bookingDate.setText(booking.getBookingDate());
        binding.bookingRestaurantName.setText(booking.getRestaurantName());
        binding.bookingTiming.setText(booking.getTimeSlot());
        binding.bookingTableType.setText(booking.getTableType()+" SEATER");
//        if(booking.getBookingDate().
//                equals(android.text.format.DateFormat.format("dd-MM-yyyy", new Date()).toString())){
//            binding.historyCard.setCardBackgroundColor(context.getResources().getColor(R.color.light_blue_100));
//        }else{
//            binding.historyCard.setCardBackgroundColor(context.getResources().getColor(R.color.yellow_100));
//        }

        binding.ivDelete.setVisibility(View.GONE);
        binding.tvDetails.setVisibility(View.GONE);
        if(mode.equals("inCompleted")){
            binding.tvMarkAsCompleted.setVisibility(View.VISIBLE);
        }else{
            binding.tvDetails.setVisibility(View.VISIBLE);
        }
        binding.tvMarkAsCompleted.setOnClickListener(v->{
            clickListener.onClick(position);
        });

        binding.tvDetails.setOnClickListener(v->{
            clickListener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public class bookingViewHolder extends RecyclerView.ViewHolder {
        public bookingViewHolder(@NonNull ItemBookingBinding itemView) {
            super(itemView.getRoot());
        }
    }
}

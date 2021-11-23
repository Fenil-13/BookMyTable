package com.digitalgenius.bookmytable.ui.Home_A.Booking_F;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.api.models.entities.Booking;
import com.digitalgenius.bookmytable.databinding.ItemBookingBinding;
import com.digitalgenius.bookmytable.interfaces.RestaurantClickListener;

import java.util.Date;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.bookingViewHolder> {

    private Context context;
    private ItemBookingBinding binding ;
    private RestaurantClickListener clickListener;
    private DiffUtil.ItemCallback<Booking> differCallback=new DiffUtil.ItemCallback<Booking>() {
        @Override
        public boolean areItemsTheSame(@NonNull Booking oldItem, @NonNull Booking newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Booking oldItem, @NonNull Booking newItem) {
            return oldItem.equals(newItem);
        }
    };

    public AsyncListDiffer differ=new AsyncListDiffer(this,differCallback);

    public BookingAdapter(Context context, RestaurantClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public bookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ItemBookingBinding.inflate(LayoutInflater.from(context),parent,false);
        return new bookingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull bookingViewHolder holder, int position) {
        Booking booking= (Booking) differ.getCurrentList().get(position);
        binding.tvDetails.setOnClickListener(v->{
            clickListener.onClick(position);
        });
        binding.bookingDate.setText(booking.getBookingDate());
        binding.bookingRestaurantName.setText(booking.getRestaurantName());
        binding.bookingTiming.setText(booking.getTimeSlot());
        binding.bookingTableType.setText(booking.getTableType()+" SEATER");

        if(booking.getBookingDate().
                equals(android.text.format.DateFormat.format("dd-MM-yyyy", new Date()).toString())){
            binding.historyCard.setCardBackgroundColor(context.getResources().getColor(R.color.light_blue_100));
        }else{
            binding.historyCard.setCardBackgroundColor(context.getResources().getColor(R.color.yellow_100));
        }

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

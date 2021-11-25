package com.digitalgenius.bookmytable.ui.MyRestaurant_A.RestaurantTable_F;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.api.models.entities.Booking;
import com.digitalgenius.bookmytable.api.models.responses.TableResponse;
import com.digitalgenius.bookmytable.databinding.ItemBookingBinding;
import com.digitalgenius.bookmytable.databinding.ItemTableBinding;
import com.digitalgenius.bookmytable.interfaces.RestaurantClickListener;

import java.util.Date;

public class RestaurantTableAdapter extends RecyclerView.Adapter<RestaurantTableAdapter.restaurantTableViewHolder> {

    private Context context;
    private ItemTableBinding binding ;
    private RestaurantClickListener clickListener;
    private DiffUtil.ItemCallback<TableResponse> differCallback=new DiffUtil.ItemCallback<TableResponse>() {
        @Override
        public boolean areItemsTheSame(@NonNull TableResponse oldItem, @NonNull TableResponse newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TableResponse oldItem, @NonNull TableResponse newItem) {
            return oldItem.equals(newItem);
        }
    };

    public AsyncListDiffer differ=new AsyncListDiffer(this,differCallback);

    public RestaurantTableAdapter(Context context, RestaurantClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public restaurantTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ItemTableBinding.inflate(LayoutInflater.from(context),parent,false);
        return new restaurantTableViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull restaurantTableViewHolder holder, int position) {
        TableResponse tableResponse= (TableResponse) differ.getCurrentList().get(position);
        binding.layoutTable.setOnClickListener(v->{
            clickListener.onClick(position);
        });
        binding.tvAvailableTable.setText("Total Table : "+tableResponse.getTableCount());
        binding.tvTableType.setText(""+tableResponse.getTableType()+" SEATER");

    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public class restaurantTableViewHolder extends RecyclerView.ViewHolder {
        public restaurantTableViewHolder(@NonNull ItemTableBinding itemView) {
            super(itemView.getRoot());
        }
    }
}

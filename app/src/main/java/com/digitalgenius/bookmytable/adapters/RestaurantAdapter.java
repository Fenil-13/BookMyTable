package com.digitalgenius.bookmytable.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalgenius.bookmytable.databinding.ItemRestaurantBinding;
import com.digitalgenius.bookmytable.interfaces.RestaurantClickListener;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.restaurantViewHolder> {

    private Context context;
    private ItemRestaurantBinding binding ;
    private RestaurantClickListener clickListener;

    public RestaurantAdapter( Context context, RestaurantClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public restaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ItemRestaurantBinding.inflate(LayoutInflater.from(context),parent,false);
        return new restaurantViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull restaurantViewHolder holder, int position) {
        binding.restaurantContainer.setOnClickListener(v->{
            clickListener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class restaurantViewHolder extends RecyclerView.ViewHolder {
        public restaurantViewHolder(@NonNull ItemRestaurantBinding itemView) {
            super(itemView.getRoot());
        }
    }
}

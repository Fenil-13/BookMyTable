package com.digitalgenius.bookmytable.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalgenius.api.models.entities.RestaurantData;
import com.digitalgenius.bookmytable.databinding.ItemRestaurantBinding;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.restaurantViewHolder> {

    private ArrayList<RestaurantData> restaurantDataList;
    private Context context;
    private ItemRestaurantBinding binding ;

    public RestaurantAdapter(ArrayList<RestaurantData> restaurantDataList, Context context) {
        this.restaurantDataList = restaurantDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public restaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ItemRestaurantBinding.inflate(LayoutInflater.from(context),parent,false);
        return new restaurantViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull restaurantViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return restaurantDataList.size();
    }

    public class restaurantViewHolder extends RecyclerView.ViewHolder {
        public restaurantViewHolder(@NonNull ItemRestaurantBinding itemView) {
            super(itemView.getRoot());
        }
    }
}

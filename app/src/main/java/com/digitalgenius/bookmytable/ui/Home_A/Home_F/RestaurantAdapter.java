package com.digitalgenius.bookmytable.ui.Home_A.Home_F;

import static com.digitalgenius.bookmytable.utils.Constants.BASE_URL;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.api.models.entities.Restaurant;
import com.digitalgenius.bookmytable.databinding.ItemRestaurantBinding;
import com.digitalgenius.bookmytable.interfaces.RestaurantClickListener;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.restaurantViewHolder> {

    private Context context;
    private ItemRestaurantBinding binding;
    private RestaurantClickListener clickListener;
    private DiffUtil.ItemCallback<Restaurant> differCallback = new DiffUtil.ItemCallback<Restaurant>() {
        @Override
        public boolean areItemsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
            return oldItem.equals(newItem);
        }
    };

    public AsyncListDiffer differ = new AsyncListDiffer(this, differCallback);

    public RestaurantAdapter(Context context, RestaurantClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public restaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemRestaurantBinding.inflate(LayoutInflater.from(context), parent, false);
        return new restaurantViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull restaurantViewHolder holder, int position) {
        Restaurant restaurant = (Restaurant) differ.getCurrentList().get(position);
        binding.restaurantContainer.setOnClickListener(v -> {
            clickListener.onClick(position);
        });
        binding.tvRestaurantHeading.setText(restaurant.getRestaurantName());
        binding.tvRestaurantLocation.setText(restaurant.getRestaurantLocation());
        binding.tvTiming.setText(restaurant.getRestaurantOpeningTime() + " - " + restaurant.getRestaurantClosingTime());
        try {
            if (restaurant.getRestaurantPics().get(0).equals("")) {

                String url = BASE_URL + "static/restaurant_profile_pic/" + restaurant.getId() + "_restaurant_profile_pic_" + restaurant.getRestaurantPics().get(0);
                Glide.with(context).load(url)
                        .into(binding.ivRestaurantPic);

            }
        } catch (Exception e) {
            Glide.with(context).load(R.drawable.dummy).into(binding.ivRestaurantPic);
        }


    }

    @Override
    public int getItemCount() {
            return differ.getCurrentList().size();
    }

    public class restaurantViewHolder extends RecyclerView.ViewHolder {
        public restaurantViewHolder(@NonNull ItemRestaurantBinding itemView) {
            super(itemView.getRoot());
        }
    }
}

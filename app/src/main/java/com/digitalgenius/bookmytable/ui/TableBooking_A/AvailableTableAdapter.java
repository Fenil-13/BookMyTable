package com.digitalgenius.bookmytable.ui.TableBooking_A;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.api.models.entities.TimeSlot;
import com.digitalgenius.bookmytable.databinding.ItemAvailableTableBinding;
import com.digitalgenius.bookmytable.interfaces.AvailableTableClickListener;

public class AvailableTableAdapter extends RecyclerView.Adapter<AvailableTableAdapter.AvailableTableViewHolder> {

    private Context context;
    private ItemAvailableTableBinding binding ;
    private AvailableTableClickListener listener;

    private DiffUtil.ItemCallback<TimeSlot> differCallback=new DiffUtil.ItemCallback<TimeSlot>() {
        @Override
        public boolean areItemsTheSame(@NonNull TimeSlot oldItem, @NonNull TimeSlot newItem) {
            return oldItem.getTime().equals(newItem.getTime());
        }

        @Override
        public boolean areContentsTheSame(@NonNull TimeSlot oldItem, @NonNull TimeSlot newItem) {
            return oldItem.equals(newItem);
        }
    };

    public AsyncListDiffer differ=new AsyncListDiffer(this,differCallback);

    public AvailableTableAdapter(Context context,AvailableTableClickListener listener) {
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public AvailableTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ItemAvailableTableBinding.inflate(LayoutInflater.from(context),parent,false);
        return new AvailableTableViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableTableViewHolder holder, int position) {
        TimeSlot timeSlot= (TimeSlot) differ.getCurrentList().get(position);
        binding.tvTimeSlot.setText(timeSlot.getTime());
        binding.tvAvailableTable.setText("Available Table :  "+ timeSlot.getAvailableTable());
//        if(position==selectedPosition){
//            activePosition=selectedPosition;
//            binding.btnChecked.setBackground(context.getDrawable(R.drawable.bg_color_primary_active));
//        }else{
//            binding.btnChecked.setBackground(context.getDrawable(R.drawable.bg_color_primary_inactive));
//        }
        binding.layoutItemAvailableTable.setOnClickListener((v)->{
            listener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public class AvailableTableViewHolder extends RecyclerView.ViewHolder {
        public AvailableTableViewHolder(@NonNull ItemAvailableTableBinding itemView) {
            super(itemView.getRoot());
        }
    }
}

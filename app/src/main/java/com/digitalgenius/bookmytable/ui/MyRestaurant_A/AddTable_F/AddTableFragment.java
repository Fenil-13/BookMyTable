package com.digitalgenius.bookmytable.ui.MyRestaurant_A.AddTable_F;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digitalgenius.bookmytable.api.models.requests.AddTableRequest;
import com.digitalgenius.bookmytable.api.models.requests.OwnerGetRestaurantTableRequest;
import com.digitalgenius.bookmytable.databinding.FragmentAddTableBinding;
import com.digitalgenius.bookmytable.databinding.FragmentRestaurantTableBinding;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantActivity;
import com.digitalgenius.bookmytable.ui.MyRestaurant_A.MyRestaurantViewModel;
import com.digitalgenius.bookmytable.utils.Functions;
import com.digitalgenius.bookmytable.utils.Resource;

public class AddTableFragment extends Fragment {
    FragmentAddTableBinding binding;
    MyRestaurantViewModel restaurantViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTableBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restaurantViewModel = ((MyRestaurantActivity) getActivity()).myRestaurantViewModel;

        setListener();

    }

    private void setListener() {
        binding.btnCreateTable.setOnClickListener((v) -> {
            if(checkFrom()){
                AddTableRequest request=new AddTableRequest(
                        restaurantViewModel.getMyRestaurantResponse().getValue().getData().getRestaurantList().get(0).getId(),
                        binding.etTableCount.getText().toString(),
                        binding.etTableType.getText().toString()
                );
                restaurantViewModel.addTable(request);
                Functions.INSTANCE.show_progress_dialog(requireContext(),"Adding Table");
            }
        });

        restaurantViewModel.getAddTableResponse().observe(getViewLifecycleOwner(),
                (response)->{
                    if (response instanceof Resource.Success) {
                        Functions.INSTANCE.hide_progress_dialog();
                        requireActivity().getOnBackPressedDispatcher().onBackPressed();
                    } else if (response instanceof Resource.Error) {
                        Functions.INSTANCE.hide_progress_dialog();
                        Functions.INSTANCE.show_long_toast(requireContext(), response.getMessage());
                    }
                });
    }

    private boolean checkFrom() {
        String tableType=binding.etTableType.getText().toString();
        String tableCount=binding.etTableCount.getText().toString();
        if(tableType.equals("") && Integer.parseInt(tableType)<0){
            Functions.INSTANCE.show_long_toast(requireContext(),"Enter Table Type");
            return false;
        }
        if(tableCount.equals("") && Integer.parseInt(tableCount)<0){
            Functions.INSTANCE.show_long_toast(requireContext(),"Enter Table Count");
            return false;
        }
        return true;
    }

}
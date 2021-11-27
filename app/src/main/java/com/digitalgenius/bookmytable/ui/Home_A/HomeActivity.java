package com.digitalgenius.bookmytable.ui.Home_A;

import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.databinding.ActivityHomeBinding;
import com.digitalgenius.bookmytable.repository.RestaurantRepository;
import com.digitalgenius.bookmytable.repository.UserRepository;
import com.digitalgenius.bookmytable.utils.Constants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.io.File;


public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    public RestaurantViewModel restaurantViewModel;

    private int CAMERA_PERMISSION_CODE = 123;
    private int READ_STORAGE_PERMISSION_CODE = 113;
    private int WRITE_STORAGE_PERMISSION_CODE = 113;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModels();
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home,R.id.navigation_profile)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void setViewModels() {
        RestaurantViewModelProviderFactory factory=
                new RestaurantViewModelProviderFactory(getApplication(),
                        new RestaurantRepository(),new UserRepository());

        restaurantViewModel= new ViewModelProvider(this,factory).get(RestaurantViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission(android.Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);


    }

    private void checkPermission(String permission , int requestCode) {
        if (ContextCompat.checkSelfPermission(
                this,
        permission
                ) == PackageManager.PERMISSION_DENIED
            ) {
            //Take Permission
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermission(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        READ_STORAGE_PERMISSION_CODE
                );
            }
        } else if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            if ( grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermission(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        WRITE_STORAGE_PERMISSION_CODE
                );
            }
        }

        else if (requestCode == WRITE_STORAGE_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT)
                    .show();
            }
        }
    }
}
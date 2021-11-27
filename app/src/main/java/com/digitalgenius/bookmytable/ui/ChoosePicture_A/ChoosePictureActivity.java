package com.digitalgenius.bookmytable.ui.ChoosePicture_A;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.digitalgenius.bookmytable.databinding.ActivityChoosePictureBinding;
import com.digitalgenius.bookmytable.utils.Constants;

import java.io.File;
import java.net.URISyntaxException;

public class ChoosePictureActivity extends AppCompatActivity {
    ActivityChoosePictureBinding binding;
    private int STORAGE_PERMISSION_CODE = 1;
    private  boolean isSelected=false;
    public static Uri fileUri;
    public static File[] restaurantPics = new File[4];
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri uri) {
            Constants.Companion.setSelectedProfilePic(true);
            fileUri=uri;
            binding.ivPreview.setImageURI(uri);
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChoosePictureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnDone.setOnClickListener((v -> {
            File uploadFile=new File(getRealPath(fileUri));
            if(getIntent().hasExtra("position")){
                if(getIntent().getStringExtra("position").equals("0")){
                    restaurantPics[0]=uploadFile;
                }else if(getIntent().getStringExtra("position").equals("1")){
                    restaurantPics[1]=uploadFile;
                }else if(getIntent().getStringExtra("position").equals("2")){
                    restaurantPics[2]=uploadFile;
                }else if(getIntent().getStringExtra("position").equals("3")){
                    restaurantPics[3]=uploadFile;
                }
            }else{
                Constants.Companion.setProfilePic(uploadFile);
            }

            onBackPressed();
            finish();
        }));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isSelected){
            isSelected=true;
            mGetContent.launch("image/*");
        }

    }


    public String getRealPath(Uri uri) {
        String docId = DocumentsContract.getDocumentId(uri);
        String[] split = docId.split(":");
        String type = split[0];
        Uri contentUri;
        switch (type) {
            case "image":
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                break;
            case "video":
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                break;
            case "audio":
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                break;
            default:
                contentUri = MediaStore.Files.getContentUri("external");
        }
        String selection = "_id=?";
        String[] selectionArgs = new String[]{
                split[1]
        };

        return getDataColumn(this, contentUri, selection, selectionArgs);
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = "_data";
        String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(column);
                String value = cursor.getString(column_index);
                if (value.startsWith("content://") || !value.startsWith("/") && !value.startsWith("file://")) {
                    return null;
                }
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}
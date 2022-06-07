package com.digitalgenius.bookmytable.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.digitalgenius.bookmytable.api.models.entities.UserData;
import com.digitalgenius.bookmytable.api.models.responses.LoginUserResponse;

public class SharedPrefManager {
    private String MyPREFERENCES="MyPREFERENCES";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private static SharedPrefManager sharePrefManager ;

    public static SharedPrefManager getInstance(Context context){
        if (sharePrefManager == null) {
            sharePrefManager = new SharedPrefManager(context);
        }
        return sharePrefManager;
    }

    SharedPrefManager(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public void setStringData(String key,String value){
        editor=sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }


    public String getStringData(String key){
        return sharedPreferences.getString(key,"");
    }


    public void setUserData(UserData data){
        editor=sharedPreferences.edit();
        editor.putString("user_auth_id",data.getUserAuthId());
        editor.putString("user_id",data.getUserId());
        editor.putString("user_device_token",data.getUserDeviceToken());
        editor.putString("user_email",data.getUserEmail());
        editor.putString("user_location",data.getUserLocation());
        editor.putString("user_name",data.getUserName());
        editor.putString("user_phone_number",data.getUserPhoneNumber());
        editor.putString("user_profile_pic",data.getUserProfilePic());
        editor.commit();
    }


    public UserData getUserData(){
       return new UserData(
               sharedPreferences.getString("user_auth_id",""),
               sharedPreferences.getString("user_device_token",""),
               sharedPreferences.getString("user_email",""),
               sharedPreferences.getString("user_id",""),
               sharedPreferences.getString("user_location",""),
               sharedPreferences.getString("user_name",""),
               sharedPreferences.getString("user_phone_number",""),
               sharedPreferences.getString("user_profile_pic","")
       );
    }
}

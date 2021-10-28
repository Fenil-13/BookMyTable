package com.digitalgenius.bookmytable.utils;

import android.content.Context;
import android.content.SharedPreferences;

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
}

package com.digitalgenius.bookmytable.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog




object Functions {
    var pDialog:SweetAlertDialog?=null
    fun show_long_toast(context: Context,msg:String){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    fun show_short_toast(context: Context,msg:String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    fun show_progress_dialog(context:Context,msg: String){
        pDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        pDialog?.apply {
            progressHelper.barColor = Color.parseColor("#F0841E")
            titleText = "$msg"
            setCancelable(false)
            show()
        }

    }

    fun hide_progress_dialog(){
        if (pDialog!=null){
            pDialog?.dismissWithAnimation()
        }

        pDialog=null
    }

}
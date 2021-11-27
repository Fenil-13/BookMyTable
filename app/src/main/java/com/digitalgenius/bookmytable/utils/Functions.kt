package com.digitalgenius.bookmytable.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


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
     fun prepareFilePart(partName: String, imgFile: File): MultipartBody.Part {


        val requestFile: RequestBody = RequestBody.create(
            "image/jpeg".toMediaTypeOrNull(),
            imgFile
        )
        return MultipartBody.Part.createFormData(partName, imgFile.getName(), requestFile);
    }

    fun createPartFromString(descriptionString: String): RequestBody {
        return RequestBody.create(okhttp3.MultipartBody.FORM, descriptionString)
    }

}
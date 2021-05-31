package com.example.bwtools.android.utils

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.Settings
import androidx.annotation.LayoutRes
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object CommonUtil {
    private const val TAG = "CommonUtils"
    const val TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss"


    fun showLoadingDialog(context: Context?, @LayoutRes progress_dialog: Int): ProgressDialog? {
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog.setContentView(progress_dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }

    fun getDeviceId(context: Context): String? {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun getTimeStamp(): String? {
        return SimpleDateFormat(TIMESTAMP_FORMAT, Locale.KOREA).format(Date())
    }
}
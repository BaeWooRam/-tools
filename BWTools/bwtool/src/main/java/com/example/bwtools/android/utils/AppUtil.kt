package com.example.bwtools.android.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object AppUtil {

    fun getVersionName(context: Context): String{
        var versionName = "Unknown"
        val packageInfo: PackageInfo

        try {
            packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            versionName = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(javaClass.simpleName, "getVersionInfo :" + e.message)
        }

        return versionName
    }

    fun openPlayStoreForApp(context: Context) {
        val appPackageName = context.packageName
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (e: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }

    fun getMarketVersionFast(packageName: String): String? {
        val mData = StringBuilder()
        var mVer: String?
        try {
            val mUrl = URL("https://play.google.com/store/apps/details?id=$packageName")
            val mConnection = mUrl.openConnection() as HttpURLConnection
                ?: return null
            mConnection.connectTimeout = 5000
            mConnection.useCaches = false
            mConnection.doOutput = true
            if (mConnection.responseCode == HttpURLConnection.HTTP_OK) {
                val mReader = BufferedReader(InputStreamReader(mConnection.inputStream))
                while (true) {
                    //TODO 수정해야함
                    val line = mReader.readLine() ?: break
                    mData.append(line)
                }
                mReader.close()
            }
            mConnection.disconnect()
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        val startToken =
            "<div class=\"BgcNfc\">Current Version</div><span class=\"htlgb\"><div class=\"IQ1z0d\"><span class=\"htlgb\">"
        val endToken = "</span></div>"
        val index = mData.indexOf(startToken)
        if (index == -1) {
            mVer = null
        } else {
            mVer = mData.substring(index + startToken.length, index + startToken.length + 100)
            mVer = mVer.substring(0, mVer.indexOf(endToken)).trim { it <= ' ' }
        }
        return mVer
    }
}
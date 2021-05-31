package com.example.bwtools.android.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

import java.net.HttpURLConnection
import java.net.URL

object NetworkUtil {
    private val tag = javaClass.simpleName

    abstract class PingAsyTask(): AsyncTask<String,Unit,Boolean>(){
        private var conn: HttpURLConnection? = null

        override fun doInBackground(vararg host: String):Boolean {
            try {
                conn = URL(host[0]).openConnection() as HttpURLConnection
                conn?.run {
                    setRequestProperty("User-Agent", "Android")
                    connectTimeout = 5000
                    connect()
                    return responseCode == 204
                }

                return false
            }catch (e:Exception) {
                Log.e(tag, "Newtwork Error ${e.message}")
                return false
            }finally {
                conn?.run {
                    disconnect()
                }
            }
        }

        override fun onPostExecute(result: Boolean) {
            if(!result){
                onDisconnect()
            }
        }

        abstract fun onDisconnect()
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun isNetwork(context: Context):Boolean{
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var isWifiConn: Boolean = false
        var isMobileConn: Boolean = false
        connMgr.allNetworks.forEach { network ->
            connMgr.getNetworkInfo(network)?.apply {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    isWifiConn = isWifiConn or isConnected
                }
                if (type == ConnectivityManager.TYPE_MOBILE) {
                    isMobileConn = isMobileConn or isConnected
                }
            }
        }

        Log.d(tag, "Wifi connected: $isWifiConn")
        Log.d(tag, "Mobile connected: $isMobileConn")
        return isMobileConn or isWifiConn
    }
}
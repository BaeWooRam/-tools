package com.example.bwtools.android.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public final class AppUtils {
    public static void openPlayStoreForApp(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static String getMarketVersionFast(String packageName) {
        StringBuilder mData = new StringBuilder();
        String mVer;
        try {
            URL mUrl = new URL("https://play.google.com/store/apps/details?id=" + packageName);
            HttpURLConnection mConnection = (HttpURLConnection) mUrl.openConnection();
            if (mConnection == null) return null;
            mConnection.setConnectTimeout(5000);
            mConnection.setUseCaches(false);
            mConnection.setDoOutput(true);
            if (mConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader mReader =
                        new BufferedReader(new InputStreamReader(mConnection.getInputStream()));
                while (true) {
                    //TODO 수정해야함
                    String line = mReader.readLine();
                    if (line == null) break;
                        mData.append(line);
                }
                mReader.close();
            }
            mConnection.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        String startToken = "<div class=\"BgcNfc\">Current Version</div><span class=\"htlgb\"><div class=\"IQ1z0d\"><span class=\"htlgb\">";
        String endToken = "</span></div>";
        int index = mData.indexOf(startToken);
        if (index == -1) {
            mVer = null;
        } else {
            mVer = mData.substring(index + startToken.length(), index + startToken.length() + 100);
            mVer = mVer.substring(0, mVer.indexOf(endToken)).trim();
        }
        return mVer;
    }
 }

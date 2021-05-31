package com.example.bwtools.android.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public final class DateUtils {

    private static final String TAG = "DateUtils";

    public static String getTransfromTimeStamp(String timestamp) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);

        String date = null;
        try {
            date = transFormat.format(transFormat.parse(timestamp));
        } catch (ParseException e) {
            throw new NullPointerException(e.toString());
        }
        return date;
    }
}

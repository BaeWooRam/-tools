/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.example.bwtools.android.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by janisharali on 27/01/17.
 */

public final class DateUtils {

    private static final String TAG = "DateUtils";

    public static String getTransfromTimeStamp(String timestamp) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

        String date = null;
        try {
            date = transFormat.format(transFormat.parse(timestamp));
        } catch (ParseException e) {
            throw new NullPointerException(e.toString());
        }
        return date;
    }
}

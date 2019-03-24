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

package com.example.bwtools.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public final class DiaryDialogUtils {
 
    public static void DiaryDialog(Context context,String message, DialogInterface.OnClickListener nogative, DialogInterface.OnClickListener positive){
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setNegativeButton("취소", nogative)
                .setPositiveButton("확인", positive)
                .show();
    }

    public static void DiaryDialog(Context context,String message){
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("확인",null)
                .show();
    }

}

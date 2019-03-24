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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.annotation.LayoutRes;

public final class ActivityUtils {
    public static final String INTENT_STR ="name";

    public static ProgressDialog showLoadingDialog(Context context, @LayoutRes int progress_dialog) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

  
    public static void GoToActivity(Activity activity, Class gotoActivity){
        Intent intent= new Intent(activity.getApplicationContext(), gotoActivity);
        activity.startActivity(intent);
    }
    public static void GoToActivity(Activity activity, String str, Class gotoActivity){
        Intent intent= new Intent(activity.getApplicationContext(), gotoActivity);
        intent.putExtra(INTENT_STR, str);
        activity.startActivity(intent);
    }

    //위에 있는 함수로는 put name을 정할수 없길래 밑에 만들었는데 잘못한 것이라면 수정할게요!
    public static void GoToActivity(Activity activity, String[] str, String name, Class gotoActivity){
        Intent intent= new Intent(activity.getApplicationContext(), gotoActivity);
        intent.putExtra(name,str);
        activity.startActivity(intent);
    }

    //제가 2가지 레이아웃을 보이고 숨기고 하는것을 자주 사용해서 만들었어요!
    public static void VisibilityView(View visible, View gone){
        visible.setVisibility(View.VISIBLE);
        gone.setVisibility(View.GONE);
    }

 

}

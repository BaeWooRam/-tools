package com.example.bwtools.android.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;


public final class ViewUtils {
    public float pxToDp(float px) {
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px / (densityDpi / 160f);
    }

    public int dpToPx(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public void changeIconDrawableToGray(Context context, Drawable drawable,@ColorRes int colorID) {
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(ContextCompat
                    .getColor(context, colorID), PorterDuff.Mode.SRC_ATOP);
        }
    }
}

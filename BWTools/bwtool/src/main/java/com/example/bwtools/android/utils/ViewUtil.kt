package com.example.bwtools.android.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

object ViewUtil {
    fun pxToDp(px: Float): Float {
        val densityDpi = Resources.getSystem().displayMetrics.densityDpi.toFloat()
        return px / (densityDpi / 160f)
    }

    fun dpToPx(dp: Float): Int {
        val density = Resources.getSystem().displayMetrics.density
        return Math.round(dp * density)
    }

    fun changeIconDrawableToGray(context: Context?, drawable: Drawable?, @ColorRes colorID: Int) {
        if (drawable != null) {
            drawable.mutate()
            drawable.setColorFilter(
                ContextCompat
                    .getColor(context!!, colorID), PorterDuff.Mode.SRC_ATOP
            )
        }
    }
}
package com.example.bwtools.android.activityresult

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

interface ForResult {
    interface Init<T: Enum<T>>{
        fun init(type:T, launcher: ActivityResultLauncher<Intent>): Launcher<T>
    }

    interface Launcher<T: Enum<T>> {
        fun launch(type: T, intent: Intent)
    }
}
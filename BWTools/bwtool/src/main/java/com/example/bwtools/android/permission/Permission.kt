package com.example.bwtools.android.permission

import android.app.Activity
import androidx.fragment.app.Fragment

interface Permission: Request, Target {
    fun excute()
}
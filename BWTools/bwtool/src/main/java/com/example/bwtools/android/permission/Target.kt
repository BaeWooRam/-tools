package com.example.bwtools.android.permission

import android.app.Activity
import androidx.fragment.app.Fragment

interface Target {
    fun target(targetActivity: Activity?): Permission?
    fun target(targetFragment: Fragment?): Permission?
}
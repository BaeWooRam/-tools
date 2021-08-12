package com.example.bwtools.android.manager.ui

import android.os.Bundle

interface BaseUiObserver {
    enum class UiType{
        LiveView, FwmController
    }

    fun getType(): UiType
    fun update(data: Bundle?)
}
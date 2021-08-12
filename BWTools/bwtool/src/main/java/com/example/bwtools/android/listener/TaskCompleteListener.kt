package com.example.bwtools.android.listener

interface TaskCompleteListener {
    fun onSuccess()
    fun onFailure(e:Exception)
}
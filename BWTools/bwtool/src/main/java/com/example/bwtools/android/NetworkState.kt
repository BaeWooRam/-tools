package com.example.bwtools.android

sealed class NetworkState<out T> {
    object Init : NetworkState<Nothing>()
    class Loading<T>(val type: ProgressType) : NetworkState<Nothing>()
    class Success<T>(val data: T) : NetworkState<T>()
    class Failure<T>(val exception: Exception) : NetworkState<T>()
    class Complete<T>(val data: T) : NetworkState<T>()
    class Cancel<T>(val exception: Exception) : NetworkState<T>()
    class Error(val throwable: Throwable?) : NetworkState<Nothing>()
}
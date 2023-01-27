package com.example.networkhandle.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketException
import java.net.UnknownHostException

abstract class ErrorHandleViewModel : ViewModel() {

    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()

        when (throwable) {
            is SocketException -> {
                Timber.tag("error").d("exceptionHandler.SocketException: ${throwable.message}")
            }
            is HttpException -> {
                Timber.tag("error").d("exceptionHandler.HttpException: ${throwable.message}")
            }
            is UnknownHostException -> {
                Timber.tag("error").d("exceptionHandler.UnknownHostException: ${throwable.message}")
            }
            else -> {
                Timber.tag("error").d("exceptionHandler.elseException: ${throwable.message}")
            }
        }
    }
}

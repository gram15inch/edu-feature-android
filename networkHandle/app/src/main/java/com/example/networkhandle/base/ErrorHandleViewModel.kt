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
            is SocketException -> {}
            is HttpException -> {}
            is UnknownHostException -> {}
            else -> {
                Timber.tag("error").d("Else exception: ${throwable.message}")
            }
        }
    }
}

package com.example.networkhandle

import android.app.Application
import android.content.SharedPreferences
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import timber.log.Timber

class TimberDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        //return "${element.fileName}:${element.lineNumber}#${element.methodName}"
        return "#${element.methodName}"
    }
}
@HiltAndroidApp
class MyApplication:Application() {
    companion object {
        // 만들어져있는 SharedPreferences 를 사용해야합니다. 재생성하지 않도록 유념해주세요
        lateinit var sSharedPreferences: SharedPreferences

        // JWT Token Header 키 값
        val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"

    }
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(TimberDebugTree())
        }

        sSharedPreferences =
            applicationContext.getSharedPreferences("SOFTSQUARED_TEMPLATE_APP", MODE_PRIVATE)
    }
}
package com.nuhlp.googlemapapi

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.nuhlp.googlemapapi.BuildConfig.NATIVE_APP_KEY


class MapsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들
        // Kakao SDK 초기화
        KakaoSdk.init(this, NATIVE_APP_KEY)
    }
}
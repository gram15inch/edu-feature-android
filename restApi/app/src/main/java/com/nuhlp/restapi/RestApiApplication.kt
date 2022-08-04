package com.nuhlp.restapi

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class RestApiApplication :Application() {

    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, "{NATIVE_APP_KEY}")
    }
}
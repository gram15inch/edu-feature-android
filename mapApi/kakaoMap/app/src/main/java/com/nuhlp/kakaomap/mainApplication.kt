package com.nuhlp.kakaomap

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
class mainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들
        // Kakao SDK 초기화
        KakaoSdk.init(this, "${BuildConfig.NATIVE_APP_KEY}")
    }
}
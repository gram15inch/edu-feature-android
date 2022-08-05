package com.nuhlp.restapi.util

import com.google.android.gms.maps.model.LatLng
import com.nuhlp.restapi.BuildConfig

object Constants {
    const val BASE_URL = "https://dapi.kakao.com/"
    const val REST_API_KEY = BuildConfig.REST_API_KEY
    val LATLNG_DONGBAEK = LatLng(37.2775928,127.1525655)
}
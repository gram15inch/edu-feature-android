package com.nuhlp.googlemapapi.util

import com.google.android.gms.maps.model.LatLng
import com.nuhlp.googlemapapi.BuildConfig

import com.nuhlp.googlemapapi.util.Constants.REST_API_KEY


object Constants {
    const val BASE_URL = "https://dapi.kakao.com/"
    const val REST_API_KEY = BuildConfig.REST_API_KEY
    val LATLNG_DONGBAEK = LatLng(37.2775928,127.1525655)
}
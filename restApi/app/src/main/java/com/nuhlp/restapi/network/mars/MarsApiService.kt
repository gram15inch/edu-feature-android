package com.nuhlp.restapi.network.mars


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET



private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MarsApiService {
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}

object MarsApi {
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}

//todo 카카오 api는 어떤 방식으로 원시 json을 얻는지 (슬래쉬 위치, 필드이름 정하는법...)
//todo 레트로핏이 어떤식으로 http 쿼리를 요청하는지
//todo moshi가 어떤식으로 json을 클래스로 변환 하는지
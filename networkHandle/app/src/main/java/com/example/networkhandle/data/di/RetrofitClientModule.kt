package com.example.networkhandle.data.di

import com.example.networkhandle.data.NullToEmptyStringAdapter
import com.example.networkhandle.data.UserApiService
import com.example.networkhandle.data.XAccessTokenInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitClientModule {

    @Singleton
    @Provides
    fun provideClient():OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .build()
    }
    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(NullToEmptyStringAdapter())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi,client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            )
            .client(client)
            .baseUrl("https://edu-api-test.softsquared.com/")
            .build()
    }

    @Provides
    fun provideRetrofitApi(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }
}
package com.example.networkhandle.data

import com.example.networkhandle.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {
    @GET("template/users")
    suspend fun getUsersWithResponse(
        @Query("word") word: String,
    ): Response<UserResponse>
}
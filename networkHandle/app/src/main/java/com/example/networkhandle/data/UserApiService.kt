package com.example.networkhandle.data

import com.example.networkhandle.data.model.signup.SignUpRequest
import com.example.networkhandle.data.model.signup.SignUpResponse
import com.example.networkhandle.data.model.user.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {
    @GET("template/users")
    suspend fun getUsersWithResponse(
        @Query("word") word: String,
    ): Response<UserResponse>

    @POST("template/users")
    suspend fun postUsersWithResponse(
        @Body params: SignUpRequest
    ): Response<SignUpResponse>


}
package com.example.networkhandle

import com.example.networkhandle.data.UserApiService
import com.example.networkhandle.data.model.signup.SignUpRequest
import com.example.networkhandle.data.model.signup.SignUpResponse
import com.example.networkhandle.data.model.user.UserResponse
import retrofit2.Response
import javax.inject.Inject


class UserRepository @Inject constructor(val userApiService: UserApiService) {


    suspend fun getUsers(word:String):Response<UserResponse>{
        return userApiService.getUsersWithResponse(word)
    }
    suspend fun postUsers(request: SignUpRequest):Response<SignUpResponse>{
        return userApiService.postUsersWithResponse(request)
    }

}
package com.example.networkhandle

import com.example.networkhandle.data.UserApiService
import com.example.networkhandle.data.model.UserResponse
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject


class UserRepository @Inject constructor(val userApiService: UserApiService) {


    suspend fun getUsers(word:String):Response<UserResponse>{
        return userApiService.getUsersWithResponse(word).apply {
            Timber.tag("response error").d("code: ${this.code()}")
            Timber.tag("response error").d("size: ${this.body()?.remoteUsers?.size?:0}")
        }
    }

}
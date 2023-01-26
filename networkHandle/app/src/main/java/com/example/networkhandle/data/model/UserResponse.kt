package com.example.networkhandle.data.model


data class UserResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val remoteUser: List<RemoteUser>
)
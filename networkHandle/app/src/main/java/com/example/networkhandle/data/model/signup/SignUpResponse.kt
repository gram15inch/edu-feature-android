package com.example.networkhandle.data.model.signup

import com.squareup.moshi.Json

data class SignUpResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    @Json(name = "result")
    val remoteSignUp: RemoteSignUp
)
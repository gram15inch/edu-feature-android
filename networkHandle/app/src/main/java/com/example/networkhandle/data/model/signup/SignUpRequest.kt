package com.example.networkhandle.data.model.signup

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpRequest(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val nickname: String,
    val phoneNumber: String
)
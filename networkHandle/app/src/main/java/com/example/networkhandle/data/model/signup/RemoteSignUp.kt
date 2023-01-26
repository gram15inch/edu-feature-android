package com.example.networkhandle.data.model.signup

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteSignUp(
    val jwt: String,
    val userId: Int
)
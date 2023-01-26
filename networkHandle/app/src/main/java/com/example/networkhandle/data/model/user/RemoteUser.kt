package com.example.networkhandle.data.model.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteUser(
    val email: String,
    val userId: Int
)
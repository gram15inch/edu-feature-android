package com.example.networkhandle.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteUser(
    val email: String,
    val userId: Int
)
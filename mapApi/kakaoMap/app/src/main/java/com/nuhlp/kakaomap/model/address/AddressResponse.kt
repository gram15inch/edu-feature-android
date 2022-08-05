package com.nuhlp.kakaomap.model.address


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressResponse(
    @Json(name = "documents")
    val locations: List<Location>,
    @Json(name = "meta")
    val meta: Meta
)
//todo 근처 가게 가져오기
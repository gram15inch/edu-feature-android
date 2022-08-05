package com.nuhlp.kakaomap.model.place


import com.nuhlp.kakaomap.model.place.Document
import com.nuhlp.kakaomap.model.place.Meta
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceResponse(
    @Json(name = "documents")
    val documents: List<Document>,
    @Json(name = "meta")
    val meta: Meta
)
package com.nuhlp.googlemapapi.network.model.place


import com.nuhlp.googlemapapi.network.model.place.Document
import com.nuhlp.googlemapapi.network.model.place.Meta
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceResponse(
    @Json(name = "documents")
    val documents: List<Document>,
    @Json(name = "meta")
    val meta: Meta
)
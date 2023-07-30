package com.apex.codeassesment.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoadUserApiResponse(
    @Json(name = "info")
    val info: Info,
    @Json(name = "results")
    val results: List<User>
)

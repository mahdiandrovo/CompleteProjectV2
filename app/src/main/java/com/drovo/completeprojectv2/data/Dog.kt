package com.drovo.completeprojectv2.data

import com.squareup.moshi.Json

data class Dog(
    @Json(name = "url")
    val url: String
)

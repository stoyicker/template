package app.home.api.breeds

import com.squareup.moshi.Json

internal class BreedResponse private constructor(
        @field:Json(name = "status")
        val status: String,
        @field:Json(name = "message")
        val message: List<String>)

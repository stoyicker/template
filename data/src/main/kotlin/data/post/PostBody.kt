package data.post

import com.squareup.moshi.Json

internal class PostBody(@field:Json(name = "something") private val something: String)

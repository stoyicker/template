package data.post

import com.squareup.moshi.Json

/**
 * Response of the post endpoint.
 */
internal class PostResponse private constructor(@field:Json(name = "something") val something: Int)

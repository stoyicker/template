package data.post

import com.squareup.moshi.Json

/**
 * Parameters for a request to the post endpoint.
 */
internal class PostParameters(@field:Json(name = "something") private val something: String)

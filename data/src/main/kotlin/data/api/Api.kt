package data.api

import data.post.PostParameters
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

internal interface Api {
    @POST("/v1/post_something")
    fun post(@Body postParameters: PostParameters): Single<ResponseBody>
}

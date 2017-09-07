package data.api

import data.post.PostBody
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface Api {
    @GET("/v1/get_something")
    fun get(): Single<ResponseBody>

    @POST("/v1/post_something")
    fun post(@Body postBody: PostBody): Single<ResponseBody>
}

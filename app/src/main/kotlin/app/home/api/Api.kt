package app.home.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

internal interface Api {
    @GET("/api/breeds/list")
    fun getBreeds(): Call<ResponseBody>
}

package app.home.api

import app.home.api.breeds.BreedResponse
import retrofit2.Call
import retrofit2.http.GET

internal interface Api {
    @GET("/api/breeds/list")
    fun getBreeds(): Call<BreedResponse>
}

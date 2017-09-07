package app.home.api

import app.home.api.breeds.BreedResponse
import app.network.NetworkClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal object ApiWrapper {
    private val delegate = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(NetworkClient.client)
            .addConverterFactory(MoshiConverterFactory.create())
            .validateEagerly(true)
            .build()
            .create(Api::class.java)

    fun getBreeds(callback: Callback<BreedResponse>) {
        delegate.getBreeds().enqueue(callback)
    }
}

private const val API_URL = "https://dog.ceo"

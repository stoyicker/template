package app.home

import app.home.api.ApiWrapper
import app.home.api.breeds.BreedMapper
import app.home.api.breeds.BreedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class BreedListCoordinator(
        private val view: BreedListCoordinator.View) {
    private val breedMapper = BreedMapper()
    private var page: Int = 0
    private var ongoingCall: Call<BreedResponse>? = null

    fun loadMore() {
        view.hideError()
        view.showLoading()
        ongoingCall = ApiWrapper.getBreeds(object : Callback<BreedResponse> {
            override fun onFailure(call: Call<BreedResponse>?, t: Throwable?) {
                stateError()
            }

            override fun onResponse(call: Call<BreedResponse>?, response: Response<BreedResponse>?) {
                if (response?.isSuccessful == true) {
                    val list = response.body()?.message
                            list?.subList(page * PAGE_SIZE,
                                    Math.min(page * PAGE_SIZE + PAGE_SIZE, list.size))
                                    ?.map { breedMapper.from(it) }
                                    ?.let {
                                        view.apply {
                                            updateContent(it)
                                            hideLoading()
                                            hideError()
                                        }
                                        page++
                                    } ?: stateError()
                } else {
                    stateError()
                }
            }

            private fun stateError() {
                view.apply {
                    hideLoading()
                    showError()
                }
            }
        })
    }

    fun destroy() {
        if (ongoingCall?.isCanceled == false) {
            ongoingCall?.cancel()
        }
    }

    interface View {
        fun showLoading()

        fun hideLoading()

        fun showError()

        fun hideError()

        fun updateContent(items: Iterable<PresentationBreed>)
    }
}

private const val PAGE_SIZE = 10

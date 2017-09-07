package app.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import app.home.api.ApiWrapper
import app.home.api.breeds.BreedMapper
import app.home.api.breeds.BreedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class BreedListCoordinator(
        recyclerView: RecyclerView,
        private val view: BreedListCoordinator.View) {
    private val breedMapper = BreedMapper()
    private var page: Int = 0

    init {
        recyclerView.adapter = BreedAdapter()
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.setHasFixedSize(false)
        // TODO Add onScrollListener to load more items
    }

    fun loadMore() {
        view.hideError()
        view.showLoading()
        ApiWrapper.getBreeds(object : Callback<BreedResponse> {
            override fun onFailure(call: Call<BreedResponse>?, t: Throwable?) {
                stateError()
            }

            override fun onResponse(call: Call<BreedResponse>?, response: Response<BreedResponse>?) {
                if (response?.isSuccessful == true) {
                    response.body()
                            ?.message
                            ?.subList(page * PAGE_SIZE, page * PAGE_SIZE + PAGE_SIZE)
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

    interface View {
        fun showLoading()

        fun hideLoading()

        fun showError()

        fun hideError()

        fun updateContent(items: Iterable<PresentationBreed>)
    }
}

private const val PAGE_SIZE = 10

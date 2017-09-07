package app.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

internal class BreedListCoordinator(recyclerView: RecyclerView, view: BreedListCoordinator.View) {
    init {
        recyclerView.adapter = BreedAdapter()
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.setHasFixedSize(false)
        // TODO Add onScrollListener to load more items
    }

    interface View {
        fun showLoading()

        fun hideLoading()

        fun showError()

        fun hideError()

        fun updateContent(items: Iterable<PresentationBreed>)
    }
}

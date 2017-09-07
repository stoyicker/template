package app.home

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import jorge.template.blank.R

internal class BreedListViewImpl(
        private val contentView: RecyclerView,
        private val guideView: View,
        private val loadingView: View,
        private val errorView: View,
        callback: Callback) : BreedListCoordinator.View {

    init {
        errorView.setOnClickListener { callback.onLoadRequested() }
    }

    override fun showLoading() {
        pushInfoArea()
        loadingView.visibility = View.VISIBLE
        guideView.visibility = View.INVISIBLE
    }

    override fun hideLoading() {
        loadingView.visibility = View.INVISIBLE
    }

    override fun showError() {
        pushInfoArea()
        errorView.visibility = View.VISIBLE
        guideView.visibility = View.INVISIBLE
    }

    override fun hideError() {
        errorView.visibility = View.GONE
    }

    override fun updateContent(items: Iterable<PresentationBreed>) {
        (contentView.adapter as BreedAdapter).add(items)
        guideView.visibility = View.VISIBLE
    }

    interface Callback {
        fun onLoadRequested()
    }

    private fun pushInfoArea() {
        (contentView.layoutParams as FrameLayout.LayoutParams).bottomMargin =
                contentView.context.resources.getDimension(R.dimen.breeds_footer_padding).toInt()
    }
}

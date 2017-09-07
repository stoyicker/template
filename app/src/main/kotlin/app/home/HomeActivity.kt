package app.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jorge.template.blank.R
import kotlinx.android.synthetic.main.activity_home.error
import kotlinx.android.synthetic.main.activity_home.list
import kotlinx.android.synthetic.main.activity_home.progress
import kotlinx.android.synthetic.main.activity_home.scroll_guide

internal class HomeActivity : AppCompatActivity(), BreedListViewImpl.Callback {
    private lateinit var breedListCoordinator: BreedListCoordinator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        breedListCoordinator = BreedListCoordinator(BreedListViewImpl(
                contentView = list,
                guideView = scroll_guide,
                loadingView = progress,
                errorView = error,
                callback = this))
        breedListCoordinator.loadMore()
    }

    override fun onDestroy() {
        super.onDestroy()
        breedListCoordinator.destroy()
    }

    override fun onLoadRequested() {
        breedListCoordinator.loadMore()
    }
}

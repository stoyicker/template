package app.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jorge.template.blank.R
import kotlinx.android.synthetic.main.activity_home.error
import kotlinx.android.synthetic.main.activity_home.list
import kotlinx.android.synthetic.main.activity_home.progress
import kotlinx.android.synthetic.main.activity_home.scroll_guide

internal class HomeActivity : AppCompatActivity() {
    private lateinit var breedListCoordinator: BreedListCoordinator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        breedListCoordinator = BreedListCoordinator(list, BreedListViewImpl(
                contentView = list,
                guideView = scroll_guide,
                loadingView = progress,
                errorView = error))
        breedListCoordinator.loadMore()
    }
}

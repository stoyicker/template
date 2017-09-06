package app.home

import android.support.test.filters.SmallTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
internal class HomeTest {
    @JvmField
    @Rule
    val activityTestRule = ActivityTestRule<HomeActivity>(HomeActivity::class.java)
    private lateinit var sut: HomeRobot

    @Before
    fun setup() {
        sut = home()
    }

    @Test
    fun screenIsVisible() {
        sut.assertShown()
    }
}

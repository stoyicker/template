package app.home

import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import app.home.HomeRobot.Companion.home
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class HomeTest {
  @JvmField
  @Rule
  val activityTestRule = ActivityTestRule<HomeActivity>(HomeActivity::class.java)
  private lateinit var subject: HomeRobot

  @Before
  fun setup() {
    subject = home()
  }

  @Test
  fun instrumentedPassWorks() {
    subject.assert(HomeRobot.Item.ROOT_CONTENT, isDisplayed())
  }

  @Test
  fun instrumentedFailWorks() {
    subject.assert(HomeRobot.Item.ROOT_CONTENT, not(isDisplayed()))
  }
}

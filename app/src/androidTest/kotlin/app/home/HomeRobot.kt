package app.home

import android.support.test.espresso.matcher.ViewMatchers.withId
import android.view.View
import app.BaseRobot
import org.hamcrest.Matcher

/**
 * An abstraction of the Home activity for testing purposes.
 */
internal class HomeRobot private constructor(): BaseRobot<HomeRobot.Item>() {
  enum class Item(override val viewMatcher: Matcher<View>) : BaseRobot.IdentifiableView {
    ROOT_CONTENT(withId(android.R.id.content))
  }

  companion object {
    /**
     * Fluent factory method, allows instances of this robot to be created either blank, for when
     * this screen is the subject, or prepared for continuation, for when this screen is a step
     * towards the subject.
     * @param steps The configuration for continuing with the screen.
     * @param R The return type for your steps
     */
    internal fun <R> home(steps: (HomeRobot.() -> R) = {
      @Suppress("UNCHECKED_CAST")
      this as R
    }) = HomeRobot().run(steps)
  }
}

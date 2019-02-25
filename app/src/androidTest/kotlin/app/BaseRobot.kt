package app

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.view.View
import org.hamcrest.Matcher

/**
 * A robot encompassing interactions that any screen robot should support.
 */
internal abstract class BaseRobot<in T : BaseRobot.IdentifiableView> {
  /**
   * Performs one or more checks in the given view.
   */
  fun assert(identifiableView: T, vararg assertions: Matcher<View>) {
    onView(identifiableView.viewMatcher).let { interaction ->
      assertions.forEach {
        interaction.check(matches(it))
      }
    }
  }

  /**
   * An interface defining a way to identify views. Usually implementations should be simple enums
   * representing the different views that can be accessed.
   */
  interface IdentifiableView {
    val viewMatcher: Matcher<View>
  }
}

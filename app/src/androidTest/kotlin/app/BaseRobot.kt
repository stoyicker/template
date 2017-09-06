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
     * Implementations should perform an assertion on whether the corresponding screen is or not
     * being shown. It is a suggestion to implement the check based on some unique screen content.
     * @param expectedShown If <code>true</code>, the implementation should assert on the screen
     * being shown; otherwise it should assert on the view *not* being shown.
     */
    abstract fun assertShown(expectedShown: Boolean = true)

    /**
     * An interface defining a way to identify views. Usually implementations should be simple enums
     * representing the different views that can be accessed.
     */
    interface IdentifiableView {
        val viewMatcher: Matcher<View>
    }
}

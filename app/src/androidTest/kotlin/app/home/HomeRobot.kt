package app.home

import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.view.View
import app.BaseRobot
import org.hamcrest.Matcher

/**
 * An abstraction of the Home activity for testing purposes.
 */
internal class HomeRobot : BaseRobot<HomeRobot.Item>() {
    override fun assertShown(expectedShown: Boolean) = assert(Item.ROOT_CONTENT, isDisplayed())

    enum class Item(override val viewMatcher: Matcher<View>) : BaseRobot.IdentifiableView {
        ROOT_CONTENT(withId(android.R.id.content))
    }
}

/**
 * Fluent factory method, allows instances of this robot to be created either blank, for when
 * this screen is the SUT, or prepared for continuation, for when this screen is a step towards the
 * SUT.
 * @param steps The configuration for continuing with the screen.
 */
internal fun home(steps: (HomeRobot.() -> Unit) = { }) = HomeRobot().also { it.steps() }


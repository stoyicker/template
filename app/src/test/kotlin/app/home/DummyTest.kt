package app.home

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

internal class DummyTest {
  @Test
  fun passWorks() {
    assertEquals("This should pass", 1 + 1, 2)
  }

  @Test
  fun failWorks() {
    assertNotEquals("This should fail", 1 + 1, 2)
  }
}

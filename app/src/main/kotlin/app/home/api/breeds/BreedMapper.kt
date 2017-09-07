package app.home.api.breeds

import app.home.PresentationBreed

internal class BreedMapper() {
    fun from(source: String) = PresentationBreed(
            breed = source,
            pictureUrl = if (System.currentTimeMillis() % 2 == 0L) {
                "http://findicons.com/files/icons/2711/free_icons_for_windows8_metro/128/test_tube.png"
            } else {
                "https://i.ytimg.com/vi/YqH3RNWnNXI/maxresdefault.jpg"
            })
}

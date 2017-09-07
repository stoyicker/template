package app.home.api.breeds

import app.home.PresentationBreed

internal class BreedMapper {
    fun from(source: String) = PresentationBreed(
            breed = source,
            pictureUrl = "https://i.ytimg.com/vi/YqH3RNWnNXI/maxresdefault.jpg")
}

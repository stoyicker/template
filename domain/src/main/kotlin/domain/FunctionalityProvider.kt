package domain

import domain.api.ApiFunctionalities

interface FunctionalityProvider {
    fun api(): ApiFunctionalities
}

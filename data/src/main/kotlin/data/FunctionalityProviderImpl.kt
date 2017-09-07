package data

import data.api.ApiFunctionalitiesImpl
import domain.FunctionalityProvider

internal object FunctionalityProviderImpl : FunctionalityProvider {
    override fun api() = ApiFunctionalitiesImpl
}

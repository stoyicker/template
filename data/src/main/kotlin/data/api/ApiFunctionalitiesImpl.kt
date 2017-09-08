package data.api

import data.post.PostSource
import domain.api.ApiFunctionalities
import domain.api.post.DomainPostParameters

internal object ApiFunctionalitiesImpl : ApiFunctionalities {
    override fun post(parameters: DomainPostParameters) = PostSource.fetch(parameters)
}

package domain.api

import domain.api.post.DomainPostParameters
import domain.api.post.DomainPostResult
import io.reactivex.Single

/**
 * Wrapper for the features that are offered by the data layer so that domain can invoke them.
 */
interface ApiFunctionalities {
    fun post(parameters: DomainPostParameters): Single<DomainPostResult>
}

package data

import io.reactivex.Single

/**
 * Describes actions that request abstractions should support.
 */
internal interface RequestFacade<in Parameters, Result> {
    fun fetch(parameters: Parameters): Single<Result>
}

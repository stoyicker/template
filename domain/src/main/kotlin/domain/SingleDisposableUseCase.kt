package domain

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver

/**
 * A use case that provides a single item or an error.
 */
abstract class SingleDisposableUseCase<T> protected constructor(
        /**
         * Send null for in-place synchronous execution. This should be decided by the calling site
         * and therefore be exposed by concrete implementations as an argument. The reason is that,
         * while the average use case called from the presentation layer needs to be executed
         * asynchronously, that's not quite the case for naturally background components, like
         * IntentService.
         */
        private val asyncExecutionScheduler: Scheduler? = null,
        private val postExecutionScheduler: Scheduler)
    : DisposableUseCase(), UseCase<Single<T>> {
    fun execute(subscriber: DisposableSingleObserver<T>) {
        assembledSubscriber = buildUseCase().let {
            val completeSetup = { x: Single<T> ->
                x.observeOn(postExecutionScheduler).subscribeWith(subscriber)
            }
            if (asyncExecutionScheduler != null) {
                completeSetup(it.subscribeOn(asyncExecutionScheduler))
            } else {
                completeSetup(it)
            }
        }
    }
}

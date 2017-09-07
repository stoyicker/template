package domain

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * A use case whose underlying "subscription" can be disposed.
 */
abstract class DisposableUseCase protected constructor() {
    protected var assembledSubscriber: Disposable = CompositeDisposable()

    fun dispose() {
        if (!assembledSubscriber.isDisposed) {
            assembledSubscriber.dispose()
        }
    }
}

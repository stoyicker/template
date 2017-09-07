package domain

/**
 * Top-level abstraction for more concrete specifications to have a guidance on what should be
 * executed.
 */
internal interface UseCase<out T> {
    fun buildUseCase(): T
}

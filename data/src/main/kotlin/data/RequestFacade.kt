package data

/**
 * Describes actions that request abstractions should support.
 */
internal interface RequestFacade<in Parameters, out Result> {
    fun fetch(parameters: Parameters): Result
}

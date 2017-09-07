package data

internal interface ObjectMapper<in A, out B> {
    fun from (source: A): B
}

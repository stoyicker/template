package app.network

import android.content.Context
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

internal object NetworkClient {
    internal lateinit var client: OkHttpClient
        private set

    fun init(context: Context) {
        client = OkHttpClient.Builder()
                .addNetworkInterceptor(shortTimeCacheInterceptor())
                .cache(Cache(context.cacheDir, 10 * 1024 * 1024))
                .build()
    }

    private fun shortTimeCacheInterceptor() = Interceptor {
        it.proceed(it.request()).newBuilder()
                .header(HEADER_CACHE_CONTROL, CacheControl.Builder()
                        .maxAge(2, TimeUnit.MINUTES)
                        .build().toString())
                .build()
    }
}

private const val HEADER_CACHE_CONTROL = "Cache-Control"

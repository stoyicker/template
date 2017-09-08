package app.network

import android.app.Application
import android.content.isInternetAvailable
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

internal object NetworkClient {
    internal lateinit var client: OkHttpClient
        private set

    fun init(application: Application) {
        client = OkHttpClient.Builder()
                .addNetworkInterceptor(shortTimeCacheInterceptor())
                .addInterceptor(offlineCacheInterceptor(application))
                .cache(Cache(application.cacheDir, 10 * 1024 * 1024))
                .build()
    }

    private fun shortTimeCacheInterceptor() = Interceptor {
        it.proceed(it.request()).newBuilder()
                .header(HEADER_CACHE_CONTROL, CacheControl.Builder()
                        .maxAge(2, TimeUnit.MINUTES)
                        .build().toString())
                .build()
    }

    private fun offlineCacheInterceptor(application: Application) = Interceptor {
        if (application.isInternetAvailable()) {
            it.proceed(it.request())
        } else {
            it.proceed(it.request().newBuilder()
                    .cacheControl(CacheControl.Builder()
                            .maxStale(1, TimeUnit.DAYS)
                            .build())
                    .build())
        }
    }
}

private const val HEADER_CACHE_CONTROL = "Cache-Control"

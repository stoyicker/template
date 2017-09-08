package android.content

import android.net.ConnectivityManager

internal fun Context.isInternetAvailable() =
        (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo?.isConnected ?: false

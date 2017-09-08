package app

import android.app.Application
import app.network.NetworkClient

internal class TemplateApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initNetworkClient()
    }

    private fun initNetworkClient() = NetworkClient.init(this)
}

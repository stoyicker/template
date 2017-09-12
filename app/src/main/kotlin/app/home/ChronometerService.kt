package app.home

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.os.Messenger

/**
 * A bound service that runs in the background, but I'm not going to use IntentService because
 * I want access to its handlerthread.
 */
internal class ChronometerService : Service() {
    private lateinit var looper: Looper
    private lateinit var handler: Handler

    override fun onCreate() {
        super.onCreate()
        val handlerThread = HandlerThread("ChronometerService HandlerThread")
        handlerThread.start()
        looper = handlerThread.looper
        handler = object : Handler(looper) {
            /**
             * Here a regular IntentService would call onHandleIntent(). We'll do something similar.
             */
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                // TODO Write command receival logic here
                stopSelf()
            }
        }
    }

    override fun onBind(p0: Intent?) = Messenger(handler).binder!!

    override fun onDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            looper.quitSafely()
        } else {
            looper.quit()
        }
    }
}

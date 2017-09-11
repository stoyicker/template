package app.music

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.support.v4.app.NotificationCompat
import android.util.Log
import app.home.HomeActivity

internal class SongDispatcherService : Service() {
    private lateinit var looper: Looper
    private lateinit var serviceHandler: Handler

    override fun onCreate() {
        super.onCreate()
        val handlerThread = HandlerThread("${SongDispatcherService::class.java.name} thread")
        handlerThread.start()
        looper = handlerThread.looper
        serviceHandler = object : Handler(looper) {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                Log.d(TAG, "I got a message")
                (msg!!.obj as Song).let {
                    startForeground(
                            NOTIFICATION_ID,
                            createNotification(songName = it.title, artist = it.artist))
                    startPlayer(name = it.title, artist = it.artist)
                }
            }
        }
    }

    override fun onBind(intent: Intent?) = Messenger(serviceHandler).binder!!

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(
                NOTIFICATION_ID,
                createNotification(
                        intent!!.extras[EXTRA_NAME] as String,
                        intent.extras[EXTRA_ARTIST] as String))
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            looper.quitSafely()
        } else {
            looper.quit()
        }
    }

    private fun startPlayer(name: String, artist: String) {
        bindService(
                Intent(this, SongPlayerService::class.java),
                object : ServiceConnection {
                    override fun onServiceConnected(
                            componentName: ComponentName?, binder: IBinder?) {
                        Log.d(TAG, "$componentName connected")
                    }

                    override fun onServiceDisconnected(componentName: ComponentName?) {
                        Log.d(TAG, "$componentName disconnected")
                    }
                },
                Context.BIND_AUTO_CREATE)
    }

    private fun createNotification(songName: String, artist: String) =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = "CHANNEL_PLAY_CONTROLS"
                val channelName = "Music Player"
                val channelImportance = NotificationManager.IMPORTANCE_MIN
                val channel = NotificationChannel(channelId, channelName, channelImportance).apply {
                    enableLights(false)
                    enableVibration(false)
                    setShowBadge(false)
                    lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                }
                (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                        .createNotificationChannel(channel)
                Notification.Builder(this, channel.id)
                        .setStyle(Notification.MediaStyle())
                        .setSmallIcon(android.R.drawable.ic_media_play)
                        .setOngoing(true)
                        .setTicker(songName)
                        .addAction(android.R.drawable.ic_media_previous, "Previous", null)
                        .addAction(android.R.drawable.ic_media_play, "Play", null)
                        .addAction(android.R.drawable.ic_media_next, "Next", null)
                        .setPriority(Notification.PRIORITY_LOW)
                        .setContentTitle(songName)
                        .setContentText(artist)
                        .setContentIntent(PendingIntent.getActivity(
                                this, 0, Intent(this, HomeActivity::class.java),
                                PendingIntent.FLAG_UPDATE_CURRENT))
                        .setStyle(Notification.MediaStyle()
                                .setShowActionsInCompactView(0, 1, 2))
                        .build()
                        .also {
                            it.flags = Notification.FLAG_FOREGROUND_SERVICE or
                                    Notification.FLAG_NO_CLEAR or
                                    Notification.FLAG_ONGOING_EVENT or
                                    Notification.FLAG_ONLY_ALERT_ONCE
                        }
            } else {
                NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_media_play)
                        .setOngoing(true)
                        .setTicker(songName)
                        .addAction(android.R.drawable.ic_media_previous, "Previous", null)
                        .addAction(android.R.drawable.ic_media_play, "Play", null)
                        .addAction(android.R.drawable.ic_media_next, "Next", null)
                        .setPriority(Notification.PRIORITY_LOW)
                        .setContentTitle(songName)
                        .setContentText(artist)
                        .setContentIntent(PendingIntent.getActivity(
                                this, 0, Intent(this, HomeActivity::class.java),
                                PendingIntent.FLAG_UPDATE_CURRENT))
                        .build()
                        .also {
                            it.flags = Notification.FLAG_FOREGROUND_SERVICE or
                                    Notification.FLAG_NO_CLEAR or
                                    Notification.FLAG_ONGOING_EVENT or
                                    Notification.FLAG_ONLY_ALERT_ONCE
                        }
            }

    companion object {
        const val SONG_DISPATCHER_ACTION_PLAY = 1
        private const val NOTIFICATION_ID = 1
        private const val EXTRA_SONG_ID = "jorge.template.blank.EXTRA_SONG_ID"
        private const val EXTRA_NAME = "jorge.template.blank.EXTRA_NAME"
        private const val EXTRA_ARTIST = "jorge.template.blank.EXTRA_ARTIST"
        fun getCallingIntent(context: Context, songId: Long, name: String, artist: String) =
                Intent(context, SongDispatcherService::class.java)
                        .putExtra(EXTRA_SONG_ID, songId)
                        .putExtra(EXTRA_NAME, name)
                        .putExtra(EXTRA_ARTIST, artist)!!
    }
}

private val TAG = SongDispatcherService::class.java.name

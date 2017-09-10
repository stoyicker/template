package app.music

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import app.home.HomeActivity

internal class SongDispatcherService : Service() {
    override fun onBind(p0: Intent?) = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(
                NOTIFICATION_ID,
                createNotification(
                        intent!!.extras[EXTRA_NAME] as String,
                        intent.extras[EXTRA_ARTIST] as String))
        return START_NOT_STICKY
    }

    private fun createNotification(songName: String, artist: String) =
            NotificationCompat.Builder(this, "CHANNEL_PLAY_CONTROLS")
                    .setSmallIcon(android.R.drawable.ic_media_play)
                    .setTicker(songName)
                    .setOngoing(true)
                    .setContentTitle(songName)
                    .setContentText(artist)
                    .setContentIntent(PendingIntent.getActivity(
                            this, 0, Intent(this, HomeActivity::class.java),
                            PendingIntent.FLAG_UPDATE_CURRENT))
                    .build()

    companion object {
        fun getCallingIntent(context: Context, songId: Long, name: String, artist: String) =
                Intent(context, SongDispatcherService::class.java)
                        .putExtra(EXTRA_SONG_ID, songId)
                        .putExtra(EXTRA_NAME, name)
                        .putExtra(EXTRA_ARTIST, artist)!!
    }
}

private const val NOTIFICATION_ID = 1
private const val EXTRA_SONG_ID = "jorge.template.blank.EXTRA_SONG_ID"
private const val EXTRA_NAME = "jorge.template.blank.EXTRA_NAME"
private const val EXTRA_ARTIST = "jorge.template.blank.EXTRA_ARTIST"

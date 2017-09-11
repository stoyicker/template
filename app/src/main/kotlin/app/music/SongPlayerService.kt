package app.music

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log

internal class SongPlayerService : Service(),
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener {
    private var mediaPlayer: MediaPlayer? = null
    private val messenger = Messenger(object : Handler(Looper.myLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            Log.d(TAG, "Received a message!")
        }
    })

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource("https://audio.simplecast.com/03dc8c4d.mp3")
            setOnPreparedListener(this@SongPlayerService)
            prepareAsync()
        }
    }

    override fun onBind(intent: Intent?) = messenger.binder!!

    override fun onCompletion(mediaPlayer: MediaPlayer?) = stopSelf()

    override fun onError(mediaPlayer: MediaPlayer?, p1: Int, p2: Int): Boolean {
        mediaPlayer?.reset()
        return false
    }

    override fun onPrepared(mediaPlayer: MediaPlayer?) {
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer!!.release()
        mediaPlayer = null
    }
}

private val TAG = SongPlayerService::class.java.name

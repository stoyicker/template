package app.home

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import app.music.ParseDeviceSongsCoordinator
import app.music.Song
import app.music.SongDispatcherService
import app.permission.PermissionRequestListener
import app.permission.PermissionRequester

internal class HomeActivity :
        AppCompatActivity(), ParseDeviceSongsCoordinator.ResultCallback, PermissionRequester {
    private val parseSongsCoordinator = ParseDeviceSongsCoordinator(this, this, this)
    private var permissionRequestListener: PermissionRequestListener? = null
    private var serviceConnection: ServiceConnection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseSongsCoordinator.actionDoParse(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceConnection?.let { unbindService(it) }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                permissionRequestListener!!.onPermissionRequestCompleted(
                        *grantResults.map { it == PackageManager.PERMISSION_GRANTED }
                                .toBooleanArray())
                permissionRequestListener = null
            }
            else -> throw IllegalStateException("what is this request code???")
        }
    }

    override fun requestPermissions(
            permissionRequestListener: PermissionRequestListener, vararg permissions: String)
            : Boolean {
        this.permissionRequestListener = permissionRequestListener
        return permissions.fold(true, { acc, current ->
            if (ContextCompat.checkSelfPermission(this, current) ==
                    PackageManager.PERMISSION_GRANTED) {
                acc
            } else {
                ActivityCompat.requestPermissions(
                        this, permissions, PERMISSION_REQUEST_CODE)
                return@fold false
            }
        })
    }

    override fun onSongsParsed(songs: Iterable<Song>) {
        songs.firstOrNull().let { it ?: Song(3L, "title", "artist") }.let {
            serviceConnection = object: ServiceConnection {
                override fun onServiceConnected(
                        componentName: ComponentName?, binder: IBinder?) {
                    Log.d(TAG, "$componentName connected")
                    Messenger(binder).send(Message.obtain().apply {
                        this.what = SongDispatcherService.SONG_DISPATCHER_ACTION_PLAY
                        this.obj = it
                    })
                }

                override fun onServiceDisconnected(componentName: ComponentName?) {
                    Log.d(TAG, "$componentName disconnected")
                }
            }
            bindService(
                    Intent(this, SongDispatcherService::class.java),
                    serviceConnection,
                    Context.BIND_AUTO_CREATE)
        }
    }
}

private val TAG = HomeActivity::class.java.name
private val PERMISSION_REQUEST_CODE = 123

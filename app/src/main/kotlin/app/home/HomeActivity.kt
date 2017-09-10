package app.home

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import app.music.ParseDeviceSongsCoordinator
import app.music.Song
import app.music.SongDispatcherService
import app.permission.PermissionRequestListener
import app.permission.PermissionRequester

internal class HomeActivity :
        AppCompatActivity(), ParseDeviceSongsCoordinator.ResultCallback, PermissionRequester {
    private val parseSongsCoordinator = ParseDeviceSongsCoordinator(this, this, this)
    private var permissionRequestListener: PermissionRequestListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseSongsCoordinator.actionDoParse(this)
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
        songs.first().let {
            SongDispatcherService.getCallingIntent(this, it.id, it.title, it.artist).let {
                if (Build.VERSION.SDK_INT >= 26) {
                    startForegroundService(it)
                } else {
                    startService(it)
                }
            }
        }
    }
}

private val PERMISSION_REQUEST_CODE = 123

package app.home

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import app.music.ParseDeviceSongsCoordinator
import app.permission.PermissionRequestListener
import app.permission.PermissionRequester

internal class HomeActivity : AppCompatActivity(), PermissionRequester {
    private val parseSongsCoordinator = ParseDeviceSongsCoordinator(this, this)
    private var permissionRequestListener: PermissionRequestListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseSongsCoordinator.actionDoParse(this)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> permissionRequestListener?.onPermissionRequestCompleted(
                    *grantResults.map { it == PackageManager.PERMISSION_GRANTED }
                            .toBooleanArray())
            else -> throw IllegalStateException("what is this request code???")
        }
    }

    override fun requestPermissions(
            permissionRequestListener: PermissionRequestListener, vararg permissions: String) =
            permissions.fold(true, { acc, current ->
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

private val PERMISSION_REQUEST_CODE = 123

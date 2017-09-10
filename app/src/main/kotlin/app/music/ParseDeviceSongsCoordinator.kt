package app.music

import android.content.Context
import app.permission.PermissionRequestListener
import app.permission.PermissionRequester

internal class ParseDeviceSongsCoordinator(
        private val context: Context,
        private val permissionRequester: PermissionRequester,
        private val resultCallback: ResultCallback)
    : PermissionRequestListener {
    override fun onPermissionRequestCompleted(vararg granted: Boolean) {
        if (granted[0]) {
            actionDoParse(context)
        } else {
            // TODO Show some error or something on a hypothetical view
        }
    }

    fun actionDoParse(context: Context) {
        if (permissionRequester.requestPermissions(
                this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            var songs = emptyList<Song>()
            context.contentResolver.query(
                    android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    null,
                    null,
                    null,
                    null).use { cursor ->
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    cursor.apply {
                        val id = getColumnIndex(android.provider.MediaStore.Audio.Media._ID)
                                .let {
                                    cursor.getLong(it)
                                }
                        val title = getColumnIndex(
                                android.provider.MediaStore.Audio.Media.TITLE).let {
                            cursor.getString(it)
                        }
                        val artist = getColumnIndex(
                                android.provider.MediaStore.Audio.Media.ARTIST).let {
                            cursor.getString(it)
                        }
                        Song(id, title, artist).let {
                            songs += it
                        }
                        moveToNext()
                    }
                }
            }
            resultCallback.onSongsParsed(songs)
        }
    }

    interface ResultCallback {
        fun onSongsParsed(songs: Iterable<Song>)
    }
}

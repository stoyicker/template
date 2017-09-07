package data

import android.content.ContentProvider
import android.content.ContentValues
import android.net.Uri

/**
 * This will get created when the app runs so we can use it as a 'hacky' way to perform
 * initialization of whatever needs it in this module.
 */
internal class InitializationContentProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        // TODO Initialize data
        return true
    }

    override fun insert(p0: Uri?, p1: ContentValues?) = null
    override fun query(
            p0: Uri?, p1: Array<out String>?, p2: String?, p3: Array<out String>?, p4: String?) =
            null
    override fun update(p0: Uri?, p1: ContentValues?, p2: String?, p3: Array<out String>?) = 0
    override fun delete(p0: Uri?, p1: String?, p2: Array<out String>?) = 0
    override fun getType(p0: Uri?) = "vnd.android.cursor.item.none"
}

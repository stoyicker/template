package app.permission

internal interface PermissionRequestListener {
    fun onPermissionRequestCompleted(vararg granted: Boolean)
}

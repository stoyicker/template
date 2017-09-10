package app.permission

internal interface PermissionRequester {
    /**
     * If the permission is already granted then nothing should happen and the implementation should
     * just return true.
     */
    fun requestPermissions(
            permissionRequestListener: PermissionRequestListener,
            vararg permissions: String): Boolean
}

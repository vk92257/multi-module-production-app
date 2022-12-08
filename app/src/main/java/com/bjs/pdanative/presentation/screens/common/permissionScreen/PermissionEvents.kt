package com.bjs.pdanative.presentation.screens.common.permissionScreen

import android.app.role.RoleManager
import com.bjs.pdanative.domain.models.permissions.Permission

sealed class PermissionEvents {
    data class PermissionGranted(val roleManager: RoleManager) : PermissionEvents()
    object PermissionDenied : PermissionEvents()
    object SettingPermissionGranted : PermissionEvents()
    object SettingPermissionDenied : PermissionEvents()
    object OpenPermissionScreen : PermissionEvents()
    object OpenSettingPermissionScreen: PermissionEvents()
object UpdatePermissionScreen : PermissionEvents()

    data class  AllowButtonClick(val data : Permission): PermissionEvents()
    data class  OnPermissionButtonClick(val index : Int): PermissionEvents()
    object ContinueButtonClick: PermissionEvents()


}

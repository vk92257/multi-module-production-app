package com.bjs.pdanative.presentation.screens.common.permissionScreen

import android.app.role.RoleManager
import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.bjs.pdanative.domain.models.permissions.Permission
import com.bjs.pdanative.domain.models.permissions.defaultAppPermissions

data class PermissionSate(
    val permission: String = RoleManager.ROLE_HOME,
    val settingScreen: String = Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS,
    val permissionList: SnapshotStateList<Permission> = mutableStateListOf<Permission>().apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            addAll(
                defaultAppPermissions
            )
        }
    },
    val openSettingScreen: Boolean = false,
    val openPermissionScreen: Boolean = false,
    val currentIndex: Int = -1,

    )
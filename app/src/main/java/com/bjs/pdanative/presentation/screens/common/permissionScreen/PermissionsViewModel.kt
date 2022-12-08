package com.bjs.pdanative.presentation.screens.common.permissionScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PermissionsViewModel : ViewModel() {
    var state by mutableStateOf(PermissionSate())
        private set


    @RequiresApi(Build.VERSION_CODES.Q)
    fun onAction(event: PermissionEvents) {
        when (event) {
            is PermissionEvents.AllowButtonClick -> {
                state = state.copy(
                    settingScreen = event.data.settingScreenToOpen,
                    permission = event.data.permissionManifestString,
                    openPermissionScreen = true,
                )
            }
            is PermissionEvents.OpenPermissionScreen -> {

            }
            is PermissionEvents.OpenSettingPermissionScreen -> {

            }
            is PermissionEvents.UpdatePermissionScreen -> {
                val list = state.permissionList
                state = state.copy(
                    permissionList = list
                )
            }

            is PermissionEvents.PermissionGranted -> {
                /*state = state.copy(
                    openPermissionScreen = false,
                    settingScreen = "",
                    permission = "",
                )*/

                if (state.currentIndex != -1) {
                    val list = state.permissionList
                    val permissionItem = list[state.currentIndex]
                    permissionItem.apply {
                        isPermissionAllowed =
                            event.roleManager.isRoleHeld(permissionItem.permissionManifestString)
                    }

                    list[state.currentIndex] = permissionItem
                    state = state.copy(permissionList = list, currentIndex = -1)

                }


            }
            is PermissionEvents.PermissionDenied -> {
                state = state.copy(
                    openPermissionScreen = false,
                    openSettingScreen = true,
                    settingScreen = "",
                    permission = "",
                )
            }
            is PermissionEvents.SettingPermissionGranted -> {
                state = state.copy(
                    openSettingScreen = false,
                    settingScreen = "",
                    permission = "",
                )
            }

            is PermissionEvents.SettingPermissionDenied -> {
                state = state.copy(
                    openSettingScreen = false,
                    settingScreen = "",
                    permission = "",
                )
            }
            is PermissionEvents.OnPermissionButtonClick -> {
                state = state.copy(
                    currentIndex = event.index
                )
            }


            is PermissionEvents.ContinueButtonClick -> {
                state.permissionList.map {
                    if (!it.isPermissionAllowed) {
//                      TODO show Error Message using snack bar

                    }
//                    TODO Navigate to the next screen

                }
            }

        }
    }
}
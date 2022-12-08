package com.bjs.pdanative.domain.models.permissions

import android.Manifest
import android.app.role.RoleManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf

data class Permission(

    var permissionMessage: String = "Require this Permission make call and access your contacts",
    var permissionTitle: String = "Permission",
    var permissionManifestString: String = "android.permission.CALL_PHONE",
    var isPermissionAllowed: Boolean = false,
    var openSettingScreen: Boolean = false,
    var shouldShowRational: Boolean = false,
    var settingScreenToOpen: String = Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS,

    )


@RequiresApi(Build.VERSION_CODES.R)
val permissions = mutableStateListOf(
    Permission(
        permissionMessage = "The BJS Launcher will be required to record the start and Finish times of calls within a log ",
        permissionTitle = "Phone Call Logs",
        permissionManifestString = Manifest.permission.CALL_PHONE,
        isPermissionAllowed = false,
    ),
    Permission(
        permissionMessage = "The BJS Launcher will need access to photos and media so that PODs and Audio can be uploaded to the cloud  ",
        permissionTitle = "Access to photos and media",
        permissionManifestString = Manifest.permission.CAMERA,
        isPermissionAllowed = false,
    ),
    Permission(
        permissionMessage = "The BJS Launcher will need access to photos and media so that PODs and Audio can be uploaded to the cloud  ",
        permissionTitle = "Allow access to manage all files",
        permissionManifestString = Manifest.permission.WRITE_EXTERNAL_STORAGE,
        isPermissionAllowed = false,
    ),

    Permission(
        permissionMessage = "BJS Launcher will record calls for Training and investigation purposes",
        permissionTitle = "Recording Audio",
        permissionManifestString = Manifest.permission.RECORD_AUDIO,
        isPermissionAllowed = false,
    ),
    Permission(
        permissionMessage = "The BJS Launcher will use this permission to track the location of drivers and the device",
        permissionTitle = "Location Permission",
        permissionManifestString = Manifest.permission.ACCESS_FINE_LOCATION,
        isPermissionAllowed = false,
    ),
    Permission(
        permissionMessage = "The BJS Launcher will need access to view and send SMS in the event there is a loss of internet connection and so a driver may contact a customer",
        permissionTitle = "Send and View SMS messages",
        permissionManifestString = Manifest.permission.SEND_SMS,
        isPermissionAllowed = false,
    ),
    Permission(
        permissionMessage = "The BJS Launcher will require access to your contacts to track calls to and from individuals",
        permissionTitle = "Contacts",
        permissionManifestString = Manifest.permission.READ_CONTACTS,
        isPermissionAllowed = false,
    ),
    /* Permission(
         permissionMessage = "Require this Permission to write the phone contacts",
         permissionTitle = "Write Contacts Permission",
         permissionManifestString = Manifest.permission.WRITE_CONTACTS,
         isPermissionAllowed = false,
     ),*/
    /*  Permission(
          permissionMessage = "Require this Permission for a smooth experience",
          permissionTitle = "Precise Phone State Permission",
          permissionManifestString = Manifest.permission.READ_PRECISE_PHONE_STATE,
          isPermissionAllowed = false,
      ),
      Permission(
          permissionMessage = "Require this Permission to write to your call-logs",
          permissionTitle = "Write Call Log Permission",
          permissionManifestString = Manifest.permission.WRITE_CALL_LOG,
          isPermissionAllowed = false,
      )*/
    /* Permission(
            permissionMessage = "Require this Permission to read from your storage",
            permissionTitle = "Read External Permission",
            permissionManifestString = Manifest.permission.READ_EXTERNAL_STORAGE,
            isPermissionAllowed = false,
        ),*/
    /* Permission(
         permissionMessage = "Require this Permission to read the phone state",
         permissionTitle = "Phone State Permission",
         permissionManifestString = Manifest.permission.READ_PHONE_STATE,
         isPermissionAllowed = false,
     ),*/


)


@RequiresApi(Build.VERSION_CODES.R)
val defaultAppPermissions = mutableStateListOf(

    Permission(
        permissionMessage = "This will make sure that the BJS app is your default launcher",
        permissionTitle = "Default Phone App",
        permissionManifestString = RoleManager.ROLE_DIALER,
        isPermissionAllowed = false,
        settingScreenToOpen = Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS
    ),


    Permission(
        permissionMessage = "When you install the BJS Launcher you will not be able to access any other apps please ensure you select BJS Launcher as your default SMS app so texts can be read and sent ",
        permissionTitle = "Default SMS App ",
        permissionManifestString = RoleManager.ROLE_SMS,
        isPermissionAllowed = false,
        settingScreenToOpen = Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS


    ),


    Permission(
        permissionMessage = "This will make sure that the BJS app is your default launcher",
        permissionTitle = "Default Home App",
        permissionManifestString = RoleManager.ROLE_HOME,
        isPermissionAllowed = false,
        settingScreenToOpen = Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS

    ),


    )





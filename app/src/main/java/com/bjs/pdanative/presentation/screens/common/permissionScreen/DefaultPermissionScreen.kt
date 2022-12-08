package com.bjs.pdanative.presentation.screens.common.permissionScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.app.role.RoleManager
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.bjs.pdanative.R
import com.bjs.pdanative.navigation.Route
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.bjs.pdanative.util.UiEvent
import com.core_ui.components.buttons.CircularButton
import com.core_ui.components.header.TitleHeader


@SuppressLint("UnspecifiedImmutableFlag", "WrongConstant")
@RequiresApi(Build.VERSION_CODES.R)
@Preview
@Composable
fun DefaultPermissionScreen(
    onNavigate: (UiEvent.Navigate) -> Unit = {},
    navigateUp: () -> Unit = {},
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    viewModel: PermissionsViewModel = hiltViewModel()
) {

    val spacer = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current
    val roleManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.getSystemService(RoleManager::class.java)
    } else {
        TODO("VERSION.SDK_INT < Q")
    }


    /*
   * Role Manager for opening setting screen
   * */

    val settingScreenIntent = Intent(viewModel.state.settingScreen)
    val settingScreenPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.getActivity(context, 0, settingScreenIntent, PendingIntent.FLAG_MUTABLE)
    } else {
        PendingIntent.getActivity(context, 0, settingScreenIntent, PendingIntent.FLAG_IMMUTABLE)
    }
    val openDefaultAppSettingScreen =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) {
            when (it.resultCode) {
                Activity.RESULT_OK -> {


                    if (state.currentIndex != -1) {
                        /*val list = state.permissionList
                        val permissionItem = list[state.currentIndex]
                        permissionItem.apply {
                            isPermissionAllowed =
                                roleManager.isRoleHeld(permissionItem.permissionManifestString)
                        }

                        list[state.currentIndex] = permissionItem
                        state = state.copy(permissionList = list, currentIndex = -1)*/

                        viewModel.onAction(PermissionEvents.PermissionGranted(roleManager))

                    }


                }
                Activity.RESULT_CANCELED -> {

                }
            }
        }


    /*
    * Role Manager for the permission
    * */


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) {
        when (it.resultCode) {
            Activity.RESULT_OK -> {
                viewModel.onAction(PermissionEvents.PermissionGranted(roleManager))

            }
            Activity.RESULT_CANCELED -> {
                openDefaultAppSettingScreen.launch(
                    IntentSenderRequest.Builder(settingScreenPendingIntent).build()
                )
            }

        }

    }


    /*Lifecycle Event */

    // Safely update the current lambdas when a new one is provided
    val lifecycleOwner = LocalLifecycleOwner.current

    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.onAction(PermissionEvents.UpdatePermissionScreen)
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(id = R.color.white)
            ),
    ) {
        LazyColumn {
            item {
                TitleHeader(
                    title = stringResource(R.string.default_permission_screen_title),
                    isShowHeadIcon = false,
                    isShowTrailerIcon = false,
                )
                Spacer(modifier = Modifier.height(spacer.spaceLarge))
            }
            itemsIndexed(state.permissionList) { index, item ->
                PermissionItem(
                    isLastIndex = index == state.permissionList.size - 1,
                    permissionMessage = item.permissionMessage,
                    permissionTitle = item.permissionTitle,
                    onAllowPermissionClick = {
                        /* allowPermission(
                             item.permissionManifestString,
                             item.settingScreenToOpen,

                             )*/

                        /*  viewModel.onAction(
                              PermissionEvents.AllowButtonClick(
                                  item
                              )
                          )*/

                        /* DefaultAppPermissions(
                              manifestStringPermission = item.permissionManifestString,
                              settingScreen = item.settingScreenToOpen,
                         )*/

                        if (roleManager.isRoleHeld(
                                item.permissionManifestString
                            )
                        ) {
                            Log.e("TAG", "DefaultAppPermissions: ")
                        } else if (roleManager.isRoleAvailable(item.permissionManifestString)) {
                            val roleIntent =
                                roleManager.createRequestRoleIntent(item.permissionManifestString)
                            val pendIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                PendingIntent.getActivity(
                                    context,
                                    0,
                                    roleIntent,
                                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT

                                )
                            } else {
                                PendingIntent.getActivity(
                                    context, 0, roleIntent, PendingIntent.FLAG_UPDATE_CURRENT
                                )
                            }

                            /* state = state.copy(
                                 currentIndex = index,
                             )*/


                            viewModel.onAction(PermissionEvents.OnPermissionButtonClick(index))

                            launcher.launch(
                                IntentSenderRequest.Builder(pendIntent).build()
                            )
                        }

                    },
                    isPermissionAllowed = if (roleManager.isRoleHeld(item.permissionManifestString)) {
                        item.isPermissionAllowed = true
                        true
                    } else {
                        item.isPermissionAllowed = false
                        false
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(spacer.spaceLarge))
                CircularButton(
                    text = stringResource(R.string.continue_text), onClick = {
                        viewModel.onAction(PermissionEvents.ContinueButtonClick)
                        onNavigate(UiEvent.Navigate(Route.DialerScreen))
                    }, modifier = Modifier.padding(
                        horizontal = 30.dp
                    )
                )
                Spacer(modifier = Modifier.height(spacer.spaceLarge))
            }
        }

        if (state.openPermissionScreen) {
            DefaultAppPermissions(settingScreen = state.settingScreen,
                manifestStringPermission = state.permission,
                onPermissionGranted = {
//                    viewModel.onAction(PermissionEvents.PermissionGranted)
                },
                onPermissionDenied = {
//                    viewModel.onAction(PermissionEvents.PermissionDenied)
                })

        }


        for (i in 0 until 10)

            if (state.openSettingScreen) {
                OpenDefaultAppSettingScreen(settingScreen = state.settingScreen,
                    onSettingPermissionGranted = {
                        viewModel.onAction(PermissionEvents.SettingPermissionGranted)
                    },
                    onSettingPermissionDenied = {
                        viewModel.onAction(PermissionEvents.SettingPermissionDenied)
                    })

            }


    }


}











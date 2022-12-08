package com.bjs.pdanative.presentation.screens.common.permissionScreen

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.bjs.pdanative.R
import com.bjs.pdanative.domain.models.permissions.Permission
import com.bjs.pdanative.domain.models.permissions.permissions
import com.bjs.pdanative.navigation.Route
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.bjs.pdanative.util.UiEvent
import com.core_ui.components.buttons.CircularButton
import com.core_ui.components.header.TitleHeader
import com.google.accompanist.permissions.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun FeatureThatRequiresCameraPermission() {

    // Camera permission state
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    when (cameraPermissionState.status) {
        // If the camera permission is granted, then show screen with the feature enabled
        PermissionStatus.Granted -> {
            Text("Camera permission Granted")
        }
        is PermissionStatus.Denied -> {
            Column {
                val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                    // If the user has denied the permission but the rationale can be shown,
                    // then gently explain why the app requires this permission
                    "The camera is important for this app. Please grant the permission."
                } else {
                    // If it's the first time the user lands on this feature, or the user
                    // doesn't want to be asked again for this permission, explain that the
                    // permission is required
                    "Camera permission required for this feature to be available. " + "Please grant the permission"
                }
                Text(textToShow)
                Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                    Text("Request permission")
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalPermissionsApi::class)
@Preview
@Composable
fun PermissionScreen(
    onNavigate: (UiEvent.Navigate) -> Unit = {},
    navigateUp: () -> Unit = {},
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {


    val coroutine = rememberCoroutineScope()


    val spacer = LocalSpacing.current
    var permissionList = remember {
        mutableStateListOf<Permission>().apply {
            addAll(permissions)
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
                val list = permissionList
                permissionList = list
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
                    title = stringResource(R.string.permission_screen_title),
                    isShowHeadIcon = false,
                    isShowTrailerIcon = false,
                )
                Spacer(modifier = Modifier.height(spacer.spaceLarge))
            }
            itemsIndexed(permissionList) { index, item ->
                PermissionItem(
                    isLastIndex = index == permissionList.size - 1,
                    permissionMessage = item.permissionMessage,
                    permissionTitle = item.permissionTitle,
                    onAllowPermissionClick = {
                        RequestPermission(
                            permissionManifestString = item.permissionManifestString,
                            openSettingScreen = {
                                if (item.shouldShowRational) {
                                    val intent =
                                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    val uri: Uri = Uri.fromParts(
                                        "package", LocalContext.current.packageName, null
                                    )
                                    intent.data = uri
                                    LocalContext.current.startActivity(intent)
                                } else {
                                    permissionList[index].shouldShowRational = true
                                }


                            },
                            onAllowPermissionClick = {
                                val item = item.copy(
                                    isPermissionAllowed = !item.isPermissionAllowed
                                )
                                val list = permissionList
                                list[index] = item
                                permissionList = list
                            },
                            onDenyPermissionClick = {

                            },
                        )
                    },
                    isPermissionAllowed = if (rememberPermissionState(permission = item.permissionManifestString).status.isGranted) {
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
                        permissionList.map {
                            if (!it.isPermissionAllowed) {
                                coroutine.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Please allow all permissions",
                                    )
                                }
                                return@CircularButton
                            }
                        }
                        onNavigate(UiEvent.Navigate(Route.DefaultPermissionScreen))
                    }, modifier = Modifier.padding(
                        horizontal = 30.dp
                    )
                )
                Spacer(modifier = Modifier.height(spacer.spaceLarge))
            }
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(
    permissionManifestString: String,
    openSettingScreen: @Composable () -> Unit = {},
    onAllowPermissionClick: () -> Unit = {},
    onDenyPermissionClick: @Composable () -> Unit = {},
) {
    val cameraPermissionState = rememberPermissionState(permission = permissionManifestString)


    LaunchedEffect(key1 = permissionManifestString, block = {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    })

    when (cameraPermissionState.status) {
        // If the camera permission is granted, then show screen with the feature enabled
        PermissionStatus.Granted -> {
            onAllowPermissionClick()

        }
        is PermissionStatus.Denied -> {
            if (cameraPermissionState.status.shouldShowRationale) {
                // If the user has denied the permission but the rationale can be shown,
                // then gently explain why the app requires this permission
                onDenyPermissionClick()
            } else {
                // If it's the first time the user lands on this feature, or the user
                // doesn't want to be asked again for this permission, explain that the
                // permission is required
                openSettingScreen()

            }
        }
    }


}


/*@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(
    permission: String,
    deniedMessage: String = "Give this app a permission to proceed. If you don't, you won't be able to use this app.",
    rationaleMessage: String = "To use this app's features, you need to give this app permission.",
) {
    val permissionState = rememberPermissionState(permission = permission)
    HandleRequest(
        permissionState = permissionState,
        deniedContent = { shouldShowRationale ->

            PermissionDeniedContent(
                deniedMessage = deniedMessage,
                rationaleMessage = rationaleMessage,
                shouldShowRationale = shouldShowRationale,
                onRequestPermission = {
                    permissionState.launchPermissionRequest()
                }
            )


        },
    ) {
        Content(
            text = "Permission granted", showButton = false,
            onClick = {

            }
        )
    }

}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HandleRequest(
    permissionState: PermissionState,
    deniedContent: @Composable (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    when (permissionState.status) {
        is PermissionStatus.Granted -> {
            content()
        }
        is PermissionStatus.Denied -> {
            deniedContent(permissionState.status.shouldShowRationale)
        }

    }

}


@Composable
fun Content(
    text: String,
    showButton: Boolean = true,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.size(12.dp))
        if (showButton) {
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Request Permission",
                    style = MaterialTheme.typography.button,
                )
            }
        }


    }
}


@Composable
fun PermissionDeniedContent(
    deniedMessage: String,
    rationaleMessage: String,
    shouldShowRationale: Boolean,
    onRequestPermission: () -> Unit,
) {
    if (shouldShowRationale) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(
                    text = "Permission Request",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                Text(
                    text = rationaleMessage,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                )
            },
            confirmButton = {
                Button(
                    onClick = onRequestPermission,
                ) {
                    Text(
                        text = "Give Permission",
                        style = MaterialTheme.typography.button,
                    )
                }
            }
        )
    } else {
        Content(text = deniedMessage, onClick = onRequestPermission)
    }
}*/




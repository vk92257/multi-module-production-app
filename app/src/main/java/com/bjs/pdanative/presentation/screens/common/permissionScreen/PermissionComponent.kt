package com.bjs.pdanative.presentation.screens.common.permissionScreen

import android.app.Activity
import android.app.PendingIntent
import android.app.role.RoleManager
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
@Preview
fun DefaultAppPermissions(
    manifestStringPermission: String = RoleManager.ROLE_HOME,
    settingScreen: String = Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS,
    onPermissionGranted: () -> Unit = {},
    onPermissionDenied: () -> Unit = {}


    /* content: @Composable() (
             (permissionString: String, settingScreenToOpen: String) -> Unit
     ) -> Unit = {},*/

) {

    val context = LocalContext.current
    val roleManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.getSystemService(RoleManager::class.java)
    } else {
        TODO("VERSION.SDK_INT < Q")
    }
    val roleIntent = roleManager.createRequestRoleIntent(manifestStringPermission)


    val pendIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.getActivity(context, 0, roleIntent, PendingIntent.FLAG_MUTABLE)
    } else {
        PendingIntent.getActivity(context, 0, roleIntent, PendingIntent.FLAG_IMMUTABLE)
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) {
        when (it.resultCode) {
            Activity.RESULT_OK -> {
                onPermissionGranted()

            }
            Activity.RESULT_CANCELED -> {
                onPermissionDenied()
            }

        }

    }


    LaunchedEffect(key1 = true)  {
//        roleIntent = roleManager.createRequestRoleIntent(manifestStringPermission)
//    SideEffect {
        if (roleManager.isRoleHeld(manifestStringPermission)) {
            Log.e("TAG", "DefaultAppPermissions: ")
        } else {
            launcher.launch(
                IntentSenderRequest.Builder(pendIntent)
                    .build()
            )
        }

    }


}


@RequiresApi(Build.VERSION_CODES.N)
@Composable
@Preview
fun OpenDefaultAppSettingScreen(
    settingScreen: String = "",
    onSettingPermissionGranted: () -> Unit = {},
    onSettingPermissionDenied: () -> Unit = {}

) {
    val context = LocalContext.current
    val settingScreenIntent = Intent(settingScreen)
    val settingScreenPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.getActivity(context, 0, settingScreenIntent, PendingIntent.FLAG_MUTABLE)
    } else {
        PendingIntent.getActivity(context, 0, settingScreenIntent, PendingIntent.FLAG_IMMUTABLE)
    }
    val openDefaultAppSettingScreen =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) {
            when (it.resultCode) {
                Activity.RESULT_OK -> {
                    onSettingPermissionGranted()
                }
                Activity.RESULT_CANCELED -> {
                    onSettingPermissionDenied()
                }
            }
        }
    LaunchedEffect(key1 = true) {
//    SideEffect {
        openDefaultAppSettingScreen.launch(
            IntentSenderRequest.Builder(settingScreenPendingIntent)
                .build()
        )

    }
}

@Composable
@Preview
fun PermissionItem(
    permissionMessage: String = "So you can make call and access your contacts",
    permissionTitle: String = "Permission",
    onAllowPermissionClick: @Composable() () -> Unit = {},
    isPermissionAllowed: Boolean = false,
    isLastIndex: Boolean = false,
) {

    var buttonClick by remember {
        mutableStateOf(false)
    }


    val spacing = LocalSpacing.current
    if (buttonClick) {
        onAllowPermissionClick()
        buttonClick = false
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = CustomBrush.vGradientGrayWhite
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 10.dp,
                    horizontal = 20.dp
                )
        ) {
            Column(
                modifier = Modifier
                    .weight(0.7f)
            ) {
                Text(
                    text = permissionTitle,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body1,
                    color = colorResource(id = R.color.text_dark)
                )

                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                Text(
                    text = permissionMessage,
                    maxLines = 4,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.body2,
                    color = colorResource(id = R.color.text_dark).copy(
                        alpha = 0.7f
                    )
                )
            }
            Column(
                modifier = Modifier
                    /*.padding(
                        start = 12.dp,
                        top = 10.dp
                    )*/
                    .align(
                        alignment = Alignment.CenterVertically
                    )
                    .weight(0.3f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row {
                    if (!isPermissionAllowed) Button(
                        border = BorderStroke(
                            1.dp,
                            color = colorResource(id = R.color.error)
                        ),
                        shape = RoundedCornerShape(
                            60.dp
                        ),
                        elevation = null,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        onClick = {
                            buttonClick = true
                        },
                    ) {
                        Text(
                            text = "Allow",
                            fontWeight = FontWeight.Bold,
                            style = TypographyEvelethClean.body2,
                            color = colorResource(id = R.color.error),
                            textAlign = TextAlign.Center,
                        )
                    }


                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    AnimatedVisibility(visible = isPermissionAllowed) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Done",
                            tint = colorResource(id = R.color.green),
                            modifier = Modifier
                                .align(
                                    alignment = Alignment.CenterVertically
                                )
                                .size(40.dp)
                        )
                    }

                }
                if (!isLastIndex) {
                    /*    Icon(
                            painter = painterResource(id = R.drawable.ic_dashed_line),
                            contentDescription = " ",
                            tint = colorResource(id = R.color.semi_gray).copy(
                                alpha = 0.4f
                            ),
                            modifier = Modifier
                                .height(
                                    height = 70.dp
                                )
                                .padding(
                                    vertical = 10.dp
                                )
 )*/
                    Spacer(modifier = Modifier.height(spacing.spaceMedium))
                    Divider(
                        color = colorResource(id = R.color.semi_gray).copy(
                            alpha = 0.4f
                        ),
                        modifier = Modifier
                            .height(
                                height = 60.dp,
                            )
                            .width(
                                width = 2.dp
                            )

                    )

                }

            }
        }


    }


}
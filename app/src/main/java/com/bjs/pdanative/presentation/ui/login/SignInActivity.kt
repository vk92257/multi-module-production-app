package com.bjs.pdanative.presentation.ui.login

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.bjs.pdanative.navigation.SetupNavGraph
import com.core.domain.workManager.UpdateAndDeleteContactManger
import com.core_ui.theme.AppTheme
import com.dialer.util.CustomForegroundService
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : ComponentActivity() {


    companion object {
        var isFromAnotherUser: Boolean = false
        var isFromChangeRole: Boolean = false
        var userList = mutableListOf<User>()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @OptIn(
        ExperimentalPermissionsApi::class, ExperimentalComposeUiApi::class
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val permissionState = rememberMultiplePermissionsState(
                    permissions = listOf(
                        Manifest.permission.CAMERA
                    )
                )
//                startService(Intent(this, CustomForegroundService::class.java))
                storingRouteData(applicationContext)

//                startActivity(Intent(this,CallActivity::class.java))
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    val scaffoldState = rememberScaffoldState()
                    Scaffold(
                        scaffoldState = scaffoldState,
                        modifier = Modifier.fillMaxSize(),
                    ) { padding ->
                        val context = LocalContext.current
//                        DialerHomeNavGraph(navController = navController)

                        SetupNavGraph(navController = navController, scaffoldState = scaffoldState)
                    }


                }


            }
        }

    }


    private fun storingRouteData(context: Context) {
        val constraints = Constraints.Builder().build()
        val data = Data.Builder()
        /*   data.putString("current_order", route_info.toString());*/
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(
            UpdateAndDeleteContactManger::class.java
        ).setConstraints(constraints).setInputData(data.build()).build()
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest)
    }


}
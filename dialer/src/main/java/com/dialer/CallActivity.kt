package com.dialer

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.core_ui.theme.AppTheme
import com.dialer.domain.model.CallLogsPojo
import com.dialer.domain.model.CallPojo
import com.dialer.domain.model.jsonToCallPojo
import com.dialer.presentation.call.CallScreen
import com.dialer.presentation.contacthistoryscreen.ContactHistoryScreen
import com.dialer.presentation.dialerhome.DialerHome
import com.dialer.util.CALL_SCREEN_DATA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CallActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    val scaffoldState = rememberScaffoldState()
                    val callScreenState: CallPojo? =
                        intent.getParcelableExtra(CALL_SCREEN_DATA)
                    Scaffold(
                        scaffoldState = scaffoldState, modifier = Modifier.fillMaxSize()
                    ) { padding ->
                        NavHost(
                            navController = navController, startDestination = "call_screen"
                        ) {
                            composable("call_screen") {
                                CallScreen(
                                    modifier = Modifier.padding(padding),
                                    onCallEnded = {
                                        finishAndRemoveTask()
                                    },
                                    callScreenState = callScreenState,
                                    navConroller = navController,
                                )
                            }


                            composable("Dialer_screen") {
                                val showDialPad = it.arguments?.getBoolean("show_dialPad") ?: false
                                DialerHome(
                                    showDialPad = showDialPad
                                )
                            }

                        }


                    }
                }

            }
        }


    }
}
package com.bjs.pdanative.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bjs.pdanative.presentation.screens.common.permissionScreen.DefaultPermissionScreen
import com.bjs.pdanative.presentation.screens.common.permissionScreen.PermissionScreen
import com.dialer.presentation.dialerhome.DialerHome

@RequiresApi(Build.VERSION_CODES.R)
@ExperimentalComposeUiApi
@Composable
fun SetupNavGraph(
    navController: NavHostController, scaffoldState: ScaffoldState
) {
    NavHost(
        navController = navController,
//        startDestination = Route.GraphLoginRoute,
        startDestination = Route.PermissionScreen,
        route = Route.GraphRootRoute
    ) {


        composable(
            route = Route.PermissionScreen
        ) {

            PermissionScreen(
                scaffoldState = scaffoldState,
                onNavigate = navController::navigate,
                navigateUp = {},
            )


        }


        composable(Route.DialerScreen) {
            DialerHome()
        }



        composable(
            route = Route.DefaultPermissionScreen
        ) {

            DefaultPermissionScreen(
                scaffoldState = scaffoldState,
                onNavigate = navController::navigate,
                navigateUp = {},
            )
        }


        loginNavGraph(
            navController = navController, scaffoldState = scaffoldState
        )
        vehiclePreCheckNavGraph(
            navController = navController, scaffoldState = scaffoldState
        )

    }
}

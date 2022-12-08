package com.dialer.presentation.dialerhome

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dialer.R
import com.dialer.domain.model.CallLogsPojo
import com.dialer.domain.model.jsonToCallPojo
import com.dialer.presentation.calllog.CallLogScreen
import com.dialer.presentation.contact.ContactScreen
import com.dialer.presentation.contacthistoryscreen.ContactHistoryScreen
import com.dialer.presentation.dialerhome.component.DialerBottomNavItem


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DialerHomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = DialerBottomNavItem.Dialer.screen_route,
    ) {

        composable(DialerBottomNavItem.Dialer.screen_route) {
            ContactScreen()
        }
        composable(DialerBottomNavItem.CallLog.screen_route) {
            CallLogScreen(
                navController = navController
            )
        }

        composable("contact_history/{contact_history}") { backStackEntity ->
            val callPojoString: String =
                backStackEntity.arguments?.getString("contact_history") ?: ""
            ContactHistoryScreen(callLogEntity = CallLogsPojo().jsonToCallPojo(
                callPojoString
            ), onBackPress = {
                navController.popBackStack()
            })
        }

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun DialerHome(
    modifier: Modifier = Modifier,
    showDialPad: Boolean = false,
    navController: NavHostController = rememberNavController(),
) {
    val items = listOf(
        DialerBottomNavItem.CallLog,
        DialerBottomNavItem.Dialer,
    )
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White, contentColor = Color.Black
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { item ->
                    BottomNavigationItem(icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.title,
                            modifier = Modifier.size(25.dp)
                        )
                    },
                        label = {
                            Text(
                                text = item.title, fontSize = 9.sp
                            )
                        },
                        selectedContentColor = colorResource(id = R.color.bjs),
                        unselectedContentColor = Color.Black.copy(0.3f),
                        alwaysShowLabel = true,
                        selected = currentRoute == item.screen_route,
                        onClick = {
                            navController.navigate(item.screen_route) {
                                navController.graph.startDestinationRoute?.let { screen_route ->
                                    popUpTo(screen_route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        })
                }
            }
        },


        ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            DialerHomeNavGraph(navController = navController)
        }


    }
}






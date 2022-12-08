package com.bjs.pdanative.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.bjs.pdanative.domain.models.camera.ImageMetaData
import com.bjs.pdanative.presentation.screens.common.camera.cameraScreen.CameraScreen
import com.bjs.pdanative.presentation.screens.loginWorkflow.driverLoginConfirmation.DriverLoginConfirmationScreen
import com.bjs.pdanative.presentation.screens.loginWorkflow.login_route.LoginRoute
import com.bjs.pdanative.presentation.screens.loginWorkflow.notification.NotificationScreen
import com.bjs.pdanative.presentation.screens.loginWorkflow.passcodVerification.PasscodeVerification
import com.bjs.pdanative.presentation.screens.loginWorkflow.routeAssignConfirmation.RouteAssigningConfirmationScreen
import com.bjs.pdanative.presentation.screens.loginWorkflow.routeStatus.component.RouteStatus
import com.bjs.pdanative.presentation.screens.loginWorkflow.successfullLogin.SuccessFullLoginScreen
import com.bjs.pdanative.presentation.screens.loginWorkflow.termsAndCondition.TermsAndConditionScreen
import com.bjs.pdanative.presentation.screens.loginWorkflow.userProfileScreen.ProfileScreen
import com.bjs.pdanative.presentation.screens.loginWorkflow.verifyPasscode.component.VerifyScreen
import com.bjs.pdanative.util.UiEvent

@OptIn(ExperimentalUnitApi::class)
fun NavGraphBuilder.loginNavGraph(
    navController: NavHostController, scaffoldState: ScaffoldState
) {
    navigation(
        startDestination = Route.ProfileScreen,
//        startDestination = Route.RouteStatus,
        route = Route.GraphLoginRoute
    ) {

        composable(route = Route.RouteStatus) {
            RouteStatus(onNavigate = navController::navigate)

        }

        composable(
            route = Route.RouteAssignConfirmationScreen
        ) {
            RouteAssigningConfirmationScreen(
                navigateUp = { navController.navigateUp() },
                scaffoldState = scaffoldState,
                navigate = navController::navigate
            )
        }

        composable(route = Route.LoginRoute) {
            LoginRoute(onNavigate = navController::navigate,
                onNavigateUp = { navController.navigateUp() })
        }
        composable(route = Route.LoginUserList) {
            /* LoggedInUserListScreen(
                 onNavigate = navController::navigate
             ) { navController.navigateUp() }*/
        }


        composable(route = Route.VerifyPasscode) {
            VerifyScreen(
                onNavigateUp = { navController.navigateUp() }, navigate = navController::navigate
            )
        }

        composable(route = Route.NotificationScreen) {
            NotificationScreen(onNavigate = navController::navigate,
                scaffoldState = scaffoldState,
                navigateUp = { navController.navigateUp() })
        }

        composable(route = Route.TermsAndCondition) {
            TermsAndConditionScreen(
                navigate = navController::navigate,
                navigateUp = { navController.navigateUp() },
                scaffoldState = scaffoldState
            )
        }

        composable(route = Route.ProfileScreen) { backStackEntry ->
            val data: List<ImageMetaData> =
                backStackEntry.savedStateHandle.get<List<ImageMetaData>>("images") ?: emptyList()


            /* LoggedInUserListScreen(
                 onNavigate = navController::navigate,
                 onNavigateUp = {})*/


            ProfileScreen(
                images = data,
                navigate = navController::navigate,
                navigateUp = { navController.navigateUp() },
                scaffoldState = scaffoldState
            )
        }
        composable(route = Route.DriverLoginConfirmationScreen) {
            DriverLoginConfirmationScreen(
                navigateUp = { navController.navigateUp() },
                scaffoldState = scaffoldState,
                navigate = navController::navigate
            )
        }

        composable(
            route = Route.SuccessfulSignInScreen
        ) {
            SuccessFullLoginScreen() {
                navController.navigate(UiEvent.Navigate(Route.LocateYourVehicle))
            }
        }

        composable(
            route = Route.PasscodeVerification
        ) {
            PasscodeVerification(
                navigateUp = { navController.navigateUp() }, navigate = navController::navigate
            )
        }

        composable(route = Route.CameraScreen + "/{singleImage}/{frontFacing}",
            arguments = listOf(navArgument("singleImage") {
                type = NavType.BoolType
            }, navArgument("frontFacing") {
                type = NavType.BoolType
            }

            )) {
            CameraScreen(
                navigateUp = { imagesList ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "images", imagesList
                    )
                    navController.popBackStack()
                },
                scaffoldState = scaffoldState,
                openFrontFacingCamera = it.arguments?.getBoolean("frontFacing") ?: true,
                isForSingleImage = it.arguments?.getBoolean("singleImage") ?: true
            )
        }

    }

}
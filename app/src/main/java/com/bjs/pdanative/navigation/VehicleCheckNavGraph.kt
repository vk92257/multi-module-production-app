package com.bjs.pdanative.navigation

import AddVehicleWalkAroundPhotos
import androidx.compose.material.ScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.assemblyKitProcess.assemblyKitScanScreen.AssemblyKitScanScreen
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.assemblyKitProcess.assemblyKitSummary.AssemblyKitSummaryScreen
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.assemblyKitProcess.assemblyKitVerificationCompleteScreen.AssemblyKitSuccessfullyVerified
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.assemblyKitProcess.assemblykitaddMannualScreen.AssemblyKitAddManualScreen
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.logDamageScreen.LogDamageScreen
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.satNavProcess.satNavSuccessfulVerificationScreen.SatNavSuccessfullyVerified
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.satNavProcess.satNavSummary.SatNavSummaryScreen
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.satNavProcess.satNavVerificationComplete.SatNavVerificationCompleteScreen
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.satNavProcess.setNavAddMannual.SatNavAddManualScreen
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.satNavProcess.setNavScanScreen.SatNavScanScreen
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.vehiclewalkaround.SuccessFulWalkAround
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.vehiclewalkaround.VehicleWalkAroundScreen
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.verificationComplete.VerificationCompleteScreen
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyStockProcess.successfullyVerifiedStock.SuccessfullyVerifiedStock
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyStockProcess.verifyStock.VerifyStock
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyVehicleProcess.arrivedAtYourVehicle.ArrivedAtYourVehicle
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyVehicleProcess.locateVehicle.LocateVehicle
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyVehicleProcess.succesfullyVerifiedVehicle.SuccessfullyVerifiedVehicle
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyVehicleProcess.verifyVehicle.VerifyVehicle
import com.bjs.pdanative.util.UiEvent

@ExperimentalComposeUiApi
fun NavGraphBuilder.vehiclePreCheckNavGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState
) {
    navigation(
//        startDestination = Route.LocateYourVehicle,
        startDestination = Route.AssemblyKitSummaryScreen,
        route = Route.GraphVehiclePreCheck
    )

    {
        composable(route = Route.LocateYourVehicle) {
            LocateVehicle(
                scaffoldState = scaffoldState,
                navigate = navController::navigate
            )

        }

        composable(route = Route.SuccessFullyVerifiedVehicle) {
            SuccessfullyVerifiedVehicle(
                navigate = navController::navigate
            )
        }

        composable(route = Route.SatNavSummaryScreen) {
            SatNavSummaryScreen(
                navigateUp = { },
                scaffoldState = scaffoldState,
                navigate = navController::navigate
            )
        }


        composable(route = Route.SatNavAssemblyKitVerificationCompletedScreen) {
            SatNavVerificationCompleteScreen(
                {}
            )
        }

        composable(
            route = Route.ArrivedAtYourVehicle
        ) {
            ArrivedAtYourVehicle(
                navigateUp = { navController.navigateUp() },
                scaffoldState = scaffoldState,
                navigate = navController::navigate
            )
        }
        composable(
            route = Route.VehicleWalkAround
        ) {
            VehicleWalkAroundScreen(
                navigateUp = { navController.navigateUp() },
                scaffoldState = scaffoldState,
                navigate = navController::navigate
            )
        }


        composable(
            route = Route.AddVehicleWalkAroundPhotos
        ) {
            AddVehicleWalkAroundPhotos(
                navigateUP = {
                    navController.navigateUp()
                    navController.navigate(UiEvent.Navigate(Route.VehicleWalkAroundSuccessful))

                }
            )
        }

        composable(
            route = Route.VehicleWalkAroundSuccessful
        ) {
            SuccessFulWalkAround(
                onProgressComplete = {
                    navController.navigate(UiEvent.Navigate(Route.SatNavScanScreen))
                }
            )
        }
        composable(
            route = Route.LogDamage
        ) {
            LogDamageScreen(
                navigateUp = { navController.navigateUp() },
                scaffoldState = scaffoldState,
                navigate = navController::navigate
            )
        }

        composable(
            route = Route.VerifyVehicle
        ) {
            VerifyVehicle(
                navigateUp = { navController.navigateUp() },
                scaffoldState = scaffoldState,
                navigate = navController::navigate
            )
        }

        composable(route = Route.VerifyStock) {
            VerifyStock(
                navigateUp = { },
                scaffoldState = scaffoldState,
                navigate = navController::navigate
            )
        }


        composable(route = Route.SuccessfullyVerifiedStockScreen) {
            SuccessfullyVerifiedStock(
                navigate = navController::navigate
            )
        }

        composable(route = Route.VerificationCompleteScreen) {
            VerificationCompleteScreen() {
//                navController.navigate(UiEvent.Navigate(Route.SatNavScanScreen))
                navController.navigate(UiEvent.Navigate(Route.VehicleWalkAround))
            }
        }



        composable(route = Route.SatNavScanScreen) {
            SatNavScanScreen(
                scaffoldState = scaffoldState,
                navigateUp = {
                    navController.navigateUp()
                },
                navigate = navController::navigate,
            )


        }

        composable(route = Route.SatNavAddManuallyScreen) {
            SatNavAddManualScreen(
                scaffoldState = scaffoldState,
                navigateUp = {
                    navController.navigateUp()
                },
                navigate = navController::navigate,
            )

        }

        composable(route = Route.SatNavVerificationSuccessfulScreen) {
            SatNavSuccessfullyVerified(navigate = navController::navigate)
        }



        composable(route = Route.AssemblyKitScanScreen) {
            AssemblyKitScanScreen(
                scaffoldState = scaffoldState,
                navigateUp = {
                    navController.navigateUp()
                },
                navigate = navController::navigate,
            )

        }


        composable(route = Route.AssemblyKitAddManualScreen) {
            AssemblyKitAddManualScreen(
                scaffoldState = scaffoldState,
                navigateUp = {
                    navController.navigateUp()
                },
                navigate = navController::navigate,
            )

        }

        composable(route = Route.AssemblyKitVerificationSuccessfulScreen) {
            AssemblyKitSuccessfullyVerified(
                navigate = navController::navigate,
            )

        }

        composable(route = Route.AssemblyKitSummaryScreen) {
            AssemblyKitSummaryScreen(
                navigateUp = { },
                scaffoldState = scaffoldState,
                navigate = navController::navigate
            )
        }
    }


}
package com.bjs.pdanative.navigation

/**
 * @Author: Vivek
 * @Date: 22/02/22
 */
object Route {

    /*
                Permission Screen
    * */
    const val PermissionScreen: String = "Permission_screen"
    const val DefaultPermissionScreen: String = "Default_permission_screen"

    /**
     *    NavGraph route
     * */
    const val GraphVehiclePreCheck: String = "Vehicle_pre_check_nav_graph"
    const val GraphRootRoute: String = "Root_graph_route"
    const val GraphLoginRoute = "Login_graph_route"

    /**
     * Screens Route
     * */


    /**
     * Login Screens
     * */
    const val TermsAndCondition: String = "Terms_and_condition"
    const val VerifyPasscode = "VerifyPasscode_screen"
    const val LoginRoute = "LoginRoute_screen"
    const val LoginUserList = "LoginUserList_screen"
    const val RouteStatus = "Route_status"
    const val NotificationScreen = "Notification_screen"
    const val ProfileScreen: String = "Profile_Screen"
    const val DriverLoginConfirmationScreen: String = "Driver_Login_Confirmation_Screen"
    const val SuccessfulSignInScreen: String = "successful_sign_in"
    const val PasscodeVerification: String = "Passcode_verification"
    const val CameraScreen: String = "camera_screen"
    const val CameraPreviewScreen: String = "camera_preview_screen"
    const val RouteAssignConfirmationScreen: String = "route_assign_confirmation_screen"

    /**
     * Pre Route Checks Screens
     * */

    /**
     * Verify Vehicle Screens
     * */
    const val LocateYourVehicle: String = "Locate_your_vehicle"
    const val ArrivedAtYourVehicle: String = "arrived_at_your_vehicle"
    const val LogDamage: String = "log_damage"
    const val VehicleWalkAround: String = "vehicle_walk_around"
    const val AddVehicleWalkAroundPhotos: String = "add_vehicle_walk_around_photos"
    const val DeleteLogDamage: String = "vehicle_walk_around_delete"
    const val ExitLogDamage: String = "exit_log_damage"
    const val VehicleWalkAroundSuccessful: String = "vehicle_walk_around_successful"
    const val VerifyVehicle: String = "Verify_vehicle"
    const val VehicleRegistrationScanError = "Vehicle_registration_scan_error"
    const val SuccessFullyVerifiedVehicle = "Success_fully_verified_vehicle"

    /**
     * Verify Stock Screens
     * */
    const val VerifyStock: String = "Verify_stock"
    const val SuccessfullyVerifiedStockScreen: String = "Successfully_verified_stock_screen"
    const val VerificationCompleteScreen: String = "Verification_complete_screen"


    /**
     * Sat Nav Screens
     * */
    const val SatNavScanScreen: String = "Sat_nav_scan_screen"
    const val SatNavAddManuallyScreen: String = "Sat_nav_add_manually_screen"
    const val SatNavVerificationSuccessfulScreen: String = "Sat_nav_verification_successful_screen"
    const val SatNavSummaryScreen: String = "sat_nav_summary_screen"
    const val SatNavAssemblyKitVerificationCompletedScreen: String =
        "Sat_nav_assembly_kit_verification_completed_screen"

    /*
    *  Assembly Kit scanning Screens
    * */

    const val AssemblyKitScanScreen: String = "Assembly_kit_scan_screen"
    const val AssemblyKitAddManualScreen: String = "Assembly_kit_add_manual_screen"
    const val AssemblyKitVerificationSuccessfulScreen: String =
        "Assembly_kit_verification_successful_screen"
    const val AssemblyKitSummaryScreen: String = "sat_nav_summary_screen"


    /*
    *   Dialer Screen Events
    * */
    const val DialerScreen : String = "Dialer_screen"



}
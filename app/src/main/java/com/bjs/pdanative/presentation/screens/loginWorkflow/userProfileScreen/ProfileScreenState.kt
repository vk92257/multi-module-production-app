package com.bjs.pdanative.presentation.screens.loginWorkflow.userProfileScreen

import com.bjs.pdanative.domain.models.notification.NotificationData
import com.bjs.pdanative.domain.models.userProfile.LoggedInUser
import com.bjs.pdanative.presentation.screens.loginWorkflow.termsAndCondition.components.TermsAndConditionData

/**
 * @Author: Vivek
 * @Date: 30/03/22
 */
data class ProfileScreenState(
    var notificationCount: Int = 15,
    var routeId : String = "13/01 B-002",
    var wareHouse : String = "Wednesbury",
    var termsAndCondition: Boolean = false,
    var agreeTermsCondition: Boolean = false,
    var isCameraOpen: Boolean = false,
    var isUserListOpen: Boolean = false,
    var loggedInUsersList: ArrayList<LoggedInUser> = arrayListOf(),
    var loggedInUser: LoggedInUser = LoggedInUser(),
    var routeRequirementMessage: String = "All required staff logged in for this route",
    var notificationList: ArrayList<NotificationData> = arrayListOf(
        NotificationData(
            message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
        )
    ),
    var roleTypeList: ArrayList<String> = arrayListOf("Driver", "Sideman", "Trainer", "Shunter"),
    var termsAndConditionDate: String = "Updated October 2021",
    var termsAndConditionList: ArrayList<TermsAndConditionData> = arrayListOf(
        TermsAndConditionData(
            message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            title = "Section title"
        )
    )
)
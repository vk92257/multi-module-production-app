package com.bjs.pdanative.presentation.screens.loginWorkflow.loggedInusers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bjs.pdanative.R
import com.bjs.pdanative.domain.models.notification.NotificationData
import com.bjs.pdanative.domain.models.userProfile.LoggedInUser
import com.bjs.pdanative.presentation.components.CustomBrush
import com.core_ui.components.buttons.GradientButton
import com.bjs.pdanative.util.UiEvent

//
// Created by gaurav on 05/04/22.
// Copyright (c) 2022 gc. All rights reserved.
//

@Composable
fun LoggedInUserListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    onNavigateUp: () -> Unit,
    routeId: String = "13/01 B-002",
    wareHouse: String = "Wednesbury",
    notification: ArrayList<NotificationData> = arrayListOf(
        NotificationData(
            message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
        )
    ),
    routeRequirementMessage: String = "All required staff logged in for this route",
    loggedInUserList: ArrayList<LoggedInUser> = arrayListOf(LoggedInUser()),

    onNotificationClick: () -> Unit = {},
    onSignOutClick: (Int) -> Unit = {},
    roleButtonClickListener: (Int) -> Unit = {},
    changeRoleButtonClickListener: (Int) -> Unit = {},
    onContinueClick: () -> Unit = {},
    onAddAnotherUserClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .background(brush = CustomBrush.vGradientGrayWhite)
            .fillMaxSize()
    ) {

        LoggedInUserToolbar(
            modifier = Modifier
                .weight(0.05f)
                .height(
                    height = 60.dp
                )
                .fillMaxWidth(),
            routeId = routeId,
            wareHouse = wareHouse,
            notificationCount = notification.size.toString().take(2),
            onNotificationClick = onNotificationClick,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .size(80.dp)
                .weight(0.06f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = routeRequirementMessage,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.text_dark),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Normal,
            )
        }
        LoggedInUserList(
            modifier = Modifier.weight(0.5f),
            loggedInUserList = loggedInUserList,
            onAddAnotherUserClick = onAddAnotherUserClick,
            onSignOutClick = onSignOutClick,
            roleButtonClickListener = roleButtonClickListener,
            changeRoleButtonClickListener = changeRoleButtonClickListener

        )
        GradientButton(
            modifier = Modifier.weight(0.3f),
            gradient = CustomBrush.hGradientYellowGreen,
            text = "CONTINUE",
            iconVisible = false,
            buttonModifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp),
            onclick = onContinueClick
        )
    }
}


@Preview
@Composable
fun LoggedInUserListScreenPreview() {
    LoggedInUserListScreen(
        onNavigate = {},
        onNavigateUp = {}
    )
}
package com.bjs.pdanative.presentation.screens.loginWorkflow.loggedInusers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bjs.pdanative.R
import com.bjs.pdanative.domain.models.userProfile.LoggedInUser
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.core_ui.components.buttons.CircularBlackButton
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean
import com.core_ui.components.NotificationWithBadge
import com.bjs.pdanative.presentation.screens.loginWorkflow.userProfileScreen.ItemSize
import com.bjs.pdanative.presentation.screens.loginWorkflow.userProfileScreen.SmallSize
import com.bjs.pdanative.presentation.screens.loginWorkflow.userProfileScreen.component.UserProfileScreenComponent

//
// Created by gaurav on 03/03/22.
// Copyright (c) 2022 gc. All rights reserved.
//
@Composable
@Preview
fun LoggedInUserScreenItem(
    modifier: Modifier = Modifier,
    id: String = "D143",
    name: String = "A.Singh",
    profileImage: String = "",
    roleText: String = "Driver",
    changeRoleText: String = "Change Role",
    signOutClick: () -> Unit = {},
    roleButtonClickListener: () -> Unit = {},
    changeRoleButtonClickListener: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth()
            .background(
                color = colorResource(id = R.color.light_grey),
                shape = RoundedCornerShape(5)
            )
            .padding(
                horizontal = spacing.spaceMedium
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.size(spacing.spaceMedium))
        TopContent(
            modifier = modifier,
            id = id,
            name = name,
            profileImage = profileImage,
            signOutClick = signOutClick
        )
        Spacer(modifier = Modifier.size(spacing.spaceExtraSmall))
        BottomContent(
            modifier = modifier,
            roleText = roleText,
            changeRoleText = changeRoleText,
            roleButtonClickListener = roleButtonClickListener,
            changeRoleButtonClickListener = changeRoleButtonClickListener
        )
        Spacer(modifier = Modifier.size(spacing.spaceMedium))
    }

}

@Composable
@Preview
fun TopContent(
    modifier: Modifier = Modifier,
    id: String = "D143",
    name: String = "A.Singh",
    profileImage: String = "",
    signOutClick: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        UserProfileScreenComponent(
            modifier = Modifier
                .align(Alignment.CenterStart),
            itemSize = ItemSize.SmallItemSize(SmallSize()),
            image = profileImage,
            showEditButton = false
        )

        Column(
            modifier = Modifier
                .align(alignment = Alignment.CenterStart)
                .padding(start = 105.dp)
        ) {
            Text(
                text = id,
                color = colorResource(id = R.color.text_grey),
                style = MaterialTheme.typography.subtitle1,
            )

            Spacer(modifier = Modifier.size(spacing.spaceSmall))

            Text(
                text = name,
                color = colorResource(id = R.color.text_dark),
                fontWeight = FontWeight.Bold,
                style = TypographyEvelethClean.body1,
            )
        }

        IconButton(
            onClick = {
                signOutClick()
            },
            modifier = Modifier
                .background(
                    brush = CustomBrush.hOrangeRedGradient,
                    shape = RoundedCornerShape(100.dp)
                )
                .align(alignment = Alignment.CenterEnd)
                .size(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_right_from_bracket),
                contentDescription = "Add icon",
                tint = Color.White, modifier = Modifier.size(25.dp)
            )
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
    }
}


@Preview
@Composable
fun BottomContent(
    modifier: Modifier = Modifier,
    roleText: String = "Driver",
    changeRoleText: String = "Change Role",
    roleButtonClickListener: () -> Unit = {},
    changeRoleButtonClickListener: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Button(
            modifier = Modifier.align(
                alignment = Alignment.CenterVertically
            ),
            onClick = {
                roleButtonClickListener()
            }, colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.button_dark_grey),
                contentColor = Color.White
            )
        ) {
            Text(
                text = roleText.uppercase(),
                style = MaterialTheme.typography.body2,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

        }

        Spacer(modifier = Modifier.width(spacing.spaceMedium))

        Button(
            border = BorderStroke(
                (0.5).dp,
                color = colorResource(id = R.color.americano)
            ),
            shape = RoundedCornerShape(
                60.dp
            ),
            elevation = null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick = {
                changeRoleButtonClickListener()
            },
            modifier = Modifier
                .height(60.dp)
                .align(
                    alignment = Alignment.CenterVertically
                )
                .fillMaxWidth()
                .padding(
                    start = 40.dp,
                )
        ) {
            Text(
                text = changeRoleText,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.body1,
                color = colorResource(id = R.color.americano),
                textAlign = TextAlign.Center,
            )
        }

    }
}


@Preview
@Composable
fun LoggedInUserToolbar(
    modifier: Modifier = Modifier,
    routeId: String = "routeId",
    wareHouse: String = "Wednesbury",
    notificationCount: String = "3",
    onNotificationClick: () -> Unit = {},
) {

    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.notification_bar)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier
                .padding(
                    horizontal = 10.dp
                )

        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_route),
                contentDescription = "",
                tint = colorResource(
                    id = R.color.route_icon_sky_blue
                ),
                modifier = Modifier
                    .size(15.dp)
                    .align(alignment = Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.size(spacing.spaceSmall))
            Text(
                text = routeId,
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.align(alignment = Alignment.CenterVertically)
            )
        }

        Row(

        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_warehouse_full),
                contentDescription = "",
                tint = colorResource(
                    id = R.color.yellow_wh_icon
                ),
                modifier = Modifier
                    .size(15.dp)
                    .align(alignment = Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.size(spacing.spaceSmall))
            Text(
                modifier = Modifier.align(
                    alignment = Alignment.CenterVertically
                ),
                text = wareHouse,
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.body2,
            )

        }

        NotificationWithBadge(
            modifier = Modifier
                .clickable {
                    onNotificationClick()
                }
                .padding(
                    top = 3.dp,
                    bottom = 3.dp,
                    end = 14.dp
                ),
            count = notificationCount,
        )
    }
}


@Preview
@Composable
fun LoggedInUserList(
    modifier: Modifier = Modifier,
    loggedInUserList: ArrayList<LoggedInUser> = arrayListOf(),
    onAddAnotherUserClick: () -> Unit = {},
    onSignOutClick: (Int) -> Unit = {},
    roleButtonClickListener: (Int) -> Unit = {},
    changeRoleButtonClickListener: (Int) -> Unit = {},
) {
    val spacing = LocalSpacing.current
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(loggedInUserList) { index, item ->
            LoggedInUserScreenItem(
                modifier = Modifier.padding(
                    vertical = 10.dp,
                    horizontal = 14.dp
                ),
                id = item.id,
                name = item.name,
                profileImage = item.image,
                roleText = item.roleText,
                changeRoleText = item.changeRoleText,
                signOutClick = { onSignOutClick(index) },
                roleButtonClickListener = { roleButtonClickListener(index) },
                changeRoleButtonClickListener = { changeRoleButtonClickListener(index) },
            )
        }

        item {
            CircularBlackButton(
                text = "ADD ANOTHER USER",
                iconVisible = false,
                onclick = onAddAnotherUserClick
            )
            Spacer(modifier = Modifier.size(spacing.spaceLarge))
        }
    }

}
package com.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core_ui.R


//
// Created by gaurav on 06/05/22.
// Copyright (c) 2022 gc. All rights reserved.
//

@Composable
@Preview
fun NotificationWithBadge(
    modifier: Modifier = Modifier,
    count: String = "0",
    bellIconColor: Int = R.color.white
) {

    Box(
        modifier = modifier

    ) {
        Row {
            Spacer(modifier = Modifier.size(18.dp))
            Box(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(20.dp)
                    .background(color = colorResource(id = R.color.bjs)),
            ) {
                Text(
                    count,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .align(alignment = Center)
                        .padding(3.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_notification_bell),
            contentDescription = "notification",
            modifier = Modifier
                .size(35.dp, 35.dp)
                .padding(top = 10.dp), tint = colorResource(id = bellIconColor)
        )
    }


}

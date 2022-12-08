package com.bjs.pdanative.presentation.screens.loginWorkflow.notification.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.components.LocalSpacing

/**
 * @Author: Vivek
 * @Date: 09/03/22
 */

@OptIn(ExperimentalUnitApi::class)
@Composable
@Preview
fun NotificationComponent(
    modifier: Modifier = Modifier, message: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    onCallToActionClick: () -> Unit = {},
    onMarkAsReadClick: () -> Unit ={},
    onNotificationTab: () -> Unit = {},
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
            )
            .clickable {

            }
            .shadow(elevation = 1.dp),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 10.dp,
                    vertical = 20.dp
                )
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_notification_bell),
                    contentDescription = "Notification",
                    modifier = Modifier
                        .background(
                            color = colorResource(id = R.color.bjs),
                            shape = RoundedCornerShape(100)
                        )
                        .size(
                            width = 35.dp,
                            height = 35.dp,
                        )
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                )

                Text(
                    text = "${stringResource(id = R.string.notificationScreenTitle)} #1",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .align(Alignment.CenterVertically)


                )

                Text(
                    text = "46mins ago",
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.semi_gray),
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Text(
                text = message,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.5.sp,
                lineHeight = 23.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 7.dp
                    )
            )



            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.blackSecondary)
                    ),
                    onClick = {
                        onCallToActionClick()
                    },
                    modifier = Modifier
                        .align(
                            alignment = Alignment.CenterVertically
                        )
                        .weight(0.5f)
                        .height(45.dp)
                        .padding(5.dp),

                ){
                   Text(text = stringResource(id = R.string.callToAction),
                   style = MaterialTheme.typography.body1,
                       fontWeight = FontWeight.Normal,
                       color = colorResource(id = R.color.white)
                       )
                }

                OutlinedCircularButtonForNotification(
                    onclick = {
                        onMarkAsReadClick()
                    },
                    modifier = Modifier
                        .align(
                            alignment = Alignment.CenterVertically
                        )
                        .weight(0.5f)
                        .padding(5.dp),
                    text = stringResource(id = R.string.markAsRead),
                    textSize = 14.sp,
                    buttonColor = Color.White,
                    borderWidth = 1.dp,
                    textColor = colorResource(id = R.color.text_dark),
                    borderColor = colorResource(id = R.color.semi_gray)
                )

            }


        }


    }
}


@Composable
@Preview
fun OutlinedCircularButtonForNotification(
    modifier: Modifier = Modifier,
    textSize: TextUnit = 16.sp,
    buttonColor: Color = colorResource(id = R.color.text_dark),
    text: String = "",
    borderColor: Color = Color.Gray,
    textColor: Color = Color.Black,
    borderWidth: Dp = 1.dp,
    onclick: (() -> Unit) = {}
) {
    Button(
        onClick = { onclick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonColor,
            contentColor = Color.White
        ),
        border = BorderStroke(
            color = borderColor,
            width = borderWidth
        ),
        shape = RoundedCornerShape(50.dp),
    ) {
        Text(
            text = text,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth(),
            color = textColor,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}

/*@OptIn(ExperimentalUnitApi::class)
@Preview
@Composable
fun NotificationComponentExpanded() {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
            )
            .shadow(elevation = 1.dp),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 10.dp,
                    vertical = 20.dp
                )
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_notification_bell),
                    contentDescription = "Notification",
                    modifier = Modifier
                        .background(
                            color = colorResource(id = R.color.bjs),
                            shape = RoundedCornerShape(100)
                        )
                        .size(
                            width = 35.dp,
                            height = 35.dp,
                        )
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                )

                Text(
                    text = "${stringResource(id = R.string.notificationScreenTitle)} #1",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .align(Alignment.CenterVertically)


                )

                Text(
                    text = "46mins ago",
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.semi_gray),
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }

            Text(
                text = stringResource(id = R.string.temp),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp),
            )



            Row(modifier = Modifier.fillMaxWidth()) {
                CircularButton(
                    onclick = {},
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(5.dp),
                    text = stringResource(id = R.string.callToAction),
                    textSize = 14.sp
                )

                OutlinedCircularButton(
                    onclick = {},
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(5.dp),
                    text = stringResource(id = R.string.markAsRead),
                    textSize = 14.sp,
                    buttonColor = Color.White,
                    borderWidth = 1.dp,
                    textColor = Color.Gray,
                    borderColor = colorResource(id = R.color.semi_gray)
                )

            }


        }


    }
}*/

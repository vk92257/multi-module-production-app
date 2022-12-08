package com.core_ui.components.header

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean
import com.core_ui.R


/**
 * @Author: Vivek
 * @Date: 21/10/21
 */
@SuppressLint("RememberReturnType")
@Composable
@Preview(showBackground = true)
fun Header(
    modifier: Modifier = Modifier,
    title: String = "Log in to route",
    backPressVisibility: Boolean = true,
    iconVisibility: Boolean = true,
    onBackPress: () -> Unit = {}
) {
    /* Column(
         modifier = Modifier
             .fillMaxWidth()
     ) {*/

    val spacing = LocalSpacing.current
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ) {


        Spacer(modifier = Modifier.size(spacing.spaceSmall))
        if (backPressVisibility) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "back",
                tint = Color.Black,
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,

                        ) {
                        onBackPress()
                    }
                    .size(25.dp, 25.dp)
                    .offset(
                        x = 5.dp,
                        y = 15.dp,
                    )
            )

        }

        if (iconVisibility) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_simple),
                contentDescription = "App Icon",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(100.dp)
                    .offset(
                        y = (-15).dp,
                    )

            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxWidth()
                .size(65.dp)
                .background(color = colorResource(id = R.color.blackSecondary)),
        ) {
            Text(
                modifier = Modifier.align(Center),
                text = title.uppercase(),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = TypographyEvelethClean.h6,
                letterSpacing = 1.sp,
            )
        }
    }


//    }
}

@Composable
@Preview
fun ToolBarHeader(
    title: String = "Notifications",
    onCloseClick: () -> Unit = {}
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(width = 50.dp, height = 64.dp)
            .background(brush = CustomBrush.vGradientGrayWhite),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title.uppercase(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 15.dp)
                .align(Alignment.CenterVertically),
            style = TypographyEvelethClean.h6,
            color = colorResource(id = R.color.blackSecondary)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = "Notification",
            modifier = Modifier
                .size(
                    width = 42.dp,
                    height = 42.dp,
                )
                .padding(end = 10.dp)
                .align(Alignment.CenterVertically)
                .clickable { onCloseClick() },
            tint = Color.Gray
        )

    }


}


@Composable
@Preview
fun NotificationToolbar(count: String = "4", onShowClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(64.dp)
            .clickable {
                onShowClick()
            },
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        /* BadgeBox(
             modifier = Modifier.align(CenterVertically),
             badgeContent = {
                 Text(
                     "8",
                   fontSize = 10.sp,
                 )
             },
             backgroundColor = colorResource(id = R.color.bjs),

         ) {
             Icon(
                 painter = painterResource(id = R.drawable.ic_notification_bell),
                 contentDescription = "notification",

                 modifier = Modifier.size(25.dp, 25.dp)
             )
         }*/
        Box(
            modifier = Modifier.align(CenterVertically)
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
            Image(
                painter = painterResource(id = R.drawable.ic_notification_bell),
                contentDescription = "notification",
                modifier = Modifier
                    .size(35.dp, 35.dp)
                    .padding(top = 10.dp)
            )
        }



        Text(
            "You have $count notifications",
            modifier = Modifier
                .align(alignment = CenterVertically),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.textSecondary)
        )

        Text(
            stringResource(R.string.show),
            modifier = Modifier
                .align(alignment = CenterVertically),
            fontWeight = FontWeight(800),
            color = colorResource(id = R.color.textSecondary),
            style = TextStyle(
                textDecoration = TextDecoration.Underline
            ),
            fontSize = 17.sp,
        )


    }
}



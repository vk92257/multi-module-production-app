package com.bjs.pdanative.presentation.screens.loginWorkflow.termsAndCondition.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean

/**
 * @Author: Vivek
 * @Date: 11/03/22
 */

@Composable
@Preview
fun TermsAndConditionComponent(
    modifier: Modifier = Modifier,
    tempText: String =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
    title: String = "Section title"
) {

    val spacing = LocalSpacing.current
    Column(
        modifier = modifier,
    ){
        Spacer(
            modifier = Modifier
                .height(spacing.spaceLarge)

        )
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.text_dark),
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(start = 15.dp),
        )

        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Text(
            text = tempText,
            lineHeight = 20.sp,
            color = colorResource(id = R.color.text_dark),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(start = 15.dp, end = 10.dp),
        )
    }
}

@Composable
@Preview
fun TermsAndConditionTitle(
    modifier: Modifier = Modifier,
    update: String = "Update",
    title: String = "Title",
    close: () -> Unit ={}
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title.uppercase(),
                color = colorResource(id = R.color.text_dark),
                style = TypographyEvelethClean.h5,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 15.dp)
                    .align(Alignment.CenterVertically)
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
                    .clickable {
                        close()
                    },
                tint = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Text(
            text = update,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(start = 15.dp),
            color = colorResource(id = R.color.text_dark).copy(
                alpha = 0.5f
            )
        )
    }


}
package com.bjs.pdanative.presentation.screens.loginWorkflow.userProfileScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.core_ui.components.buttons.CircularButton
import com.core_ui.components.buttons.GradientButton
import com.core_ui.components.dropDowns.CustomDropDown
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean
import com.bjs.pdanative.presentation.components.header.NotificationToolbar
import com.bjs.pdanative.presentation.screens.loginWorkflow.userProfileScreen.component.TermsAndConditionView
import com.bjs.pdanative.presentation.screens.loginWorkflow.userProfileScreen.component.UserProfileScreenComponent

@Composable
@Preview
fun ProfileScreen(
    notificationCount: String = "15",
    onNotificationClick: () -> Unit = {},
    profileImage: String = "",
    onEditButtonClick: () -> Unit = {},
    id: String = "D143",
    name: String = "A.singh",
    suggestions: ArrayList<String> = arrayListOf("Driver", "Sideman", "Trainer", "Sunter"),
    onDropDownItemClick: (String) -> Unit = {},
    dropDownPlaceHolder: String = "Driver",
    onTermsAndConditionClick: () -> Unit = {},
    onCheckChanged: (state: Boolean) -> Unit = {},
    onContinueClick: () -> Unit = {},
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .background(CustomBrush.vGradientGrayWhite)
            .fillMaxSize()
            .verticalScroll(
                enabled = true,
                reverseScrolling = false,
                state = scrollState
            )
    ) {
        NotificationToolbar(
            count = notificationCount,
//            onShowClick = onNotificationClick,
            onShowClick = {
            }
        )

        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        UserProfileScreenComponent(
            image = profileImage,
            modifier = Modifier.align(
                alignment = Alignment.CenterHorizontally,
            ),
            ItemSize.LargeItemSize(LargeSize()),
            editButtonClick = onEditButtonClick
        )

        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Text(
            text = id,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            style = MaterialTheme.typography.body1,
            color = colorResource(id = R.color.textSecondary)
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))

        Text(
            text = name,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            style = TypographyEvelethClean.h4,
            fontWeight = FontWeight.Normal,
        )

        CustomDropDown(
            suggestions = suggestions,
            modifier = Modifier.padding(40.dp),
            placeHolder = dropDownPlaceHolder,
        )

        Spacer(modifier = Modifier.size(spacing.spaceMedium))

        CircularButton(
            text = stringResource(R.string.terms_and_condition).uppercase(),
            textSize = 16.sp,
            modifier = Modifier.padding(
                horizontal = 30.dp
            ),
            onClick = onTermsAndConditionClick
        )

        Spacer(modifier = Modifier.size(spacing.spaceSmall))

        TermsAndConditionView(
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                )
                .padding(
                    end = 20.dp
                ),
            onCheckedChange = onCheckChanged,
        )

        Spacer(modifier = Modifier.size(spacing.spaceMedium))

        GradientButton(
            text = stringResource(id = R.string.continue_text).uppercase(),
            gradient = CustomBrush.hGradientYellowGreen,
            buttonModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 10.dp),
            iconVisible = false,
            onclick = onContinueClick
        )
        Spacer(modifier = Modifier.size(spacing.spaceExtraLarge))
    }
}
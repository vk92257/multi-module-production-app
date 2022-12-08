package com.bjs.pdanative.presentation.screens.loginWorkflow.termsAndCondition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.core_ui.components.buttons.GradientSquareButton
import com.bjs.pdanative.presentation.screens.loginWorkflow.termsAndCondition.components.TermsAndConditionComponent
import com.bjs.pdanative.presentation.screens.loginWorkflow.termsAndCondition.components.TermsAndConditionTitle
import com.bjs.pdanative.util.UiEvent

/**
 * @Author: Vivek
 * @Date: 10/03/22
 */

@Composable
fun TermsAndConditionScreen(
    viewModel: TermsAndConditionViewModel = hiltViewModel(),
    navigate: (UiEvent.Navigate) -> Unit,
    navigateUp: () -> Unit,
    scaffoldState: ScaffoldState,
) {
    val spacing = LocalSpacing.current
    val title = "Terms & Conditions"
    val update = "Updated October 2021"


    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Navigate -> navigate(it)
                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = CustomBrush.vGradientGrayWhite
            )
    ) {

        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        TermsAndConditionTitle(
            update = update,
            title = title,
            close = {
                navigateUp()
            }
        )

        Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
        LazyColumn(
            modifier = Modifier
                .weight(7f)
                .padding(bottom = 20.dp)
        ) {
            items(3) {
                TermsAndConditionComponent()
            }
        }

        GradientSquareButton(
            modifier = Modifier.weight(2f),
            text = "i agree",
            onclick = navigateUp
        )


    }
}


@Preview
@Composable
fun Preview() {
    TermsAndConditionScreen(
        navigate = {},
        navigateUp = {  },
        scaffoldState = rememberScaffoldState()
    )
}
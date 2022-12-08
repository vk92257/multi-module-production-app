package com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.logDamageScreen.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.core_ui.components.buttons.CircularButton
import com.core_ui.components.buttons.GradientButton

@Composable
@Preview
fun LogDamageFooterItem(onSaveClick: () -> Unit = {}, onAddMoreDamageClick: () -> Unit = {}) {
    val localSpacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 20.dp)
    ) {
        CircularButton(
            text = "Log more Damage",
            onClick = {
                onAddMoreDamageClick()
            }
        )
        Spacer(modifier = Modifier.size(localSpacing.spaceTwenty))
        GradientButton(
            text = "Save & Continue",
            buttonModifier = Modifier
                .fillMaxWidth()
                .size(60.dp),
            gradient = CustomBrush.lGradientGreenAndLightGreen,
            iconVisible = false,
            onclick = {
                onSaveClick()
            }
        )
    }

}
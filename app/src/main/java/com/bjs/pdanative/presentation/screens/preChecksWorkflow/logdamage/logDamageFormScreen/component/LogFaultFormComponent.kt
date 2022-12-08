package com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.logDamageFormScreen.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.core_ui.components.dropDowns.CustomDropDown

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LogDamageForm(
    damagedComponentList: List<String> = listOf("mirror", "Medium", "Low"),
    priorityList: List<String> = listOf("High", "Medium", "Low"),
    onAdditionalInfoChange: (String) -> Unit = {},
    onDamagedComponentSelected: (String) -> Unit = {},
    onPrioritySelected: (String) -> Unit = {},
    additionalNotes: String = "",
    priority: String = "Priority",
    damagedComponent: String = "Damaged Component",
    softwareKeyboardController: SoftwareKeyboardController
) {
    val localSpacing = LocalSpacing.current

    Column {
        CustomDropDown(
            placeHolder = damagedComponent,
            suggestions = damagedComponentList,
            onItemClick = {
                onDamagedComponentSelected(it)
            })
        Spacer(modifier = Modifier.size(localSpacing.spaceMedium))
        CustomDropDown(
            placeHolder = priority,
            suggestions = priorityList,
            onItemClick = {
                onPrioritySelected(it)
            })
        Spacer(modifier = Modifier.size(localSpacing.spaceMedium))
        OutlinedTextField(
            value = additionalNotes,
            placeholder = { Text(text = stringResource(R.string.log_damage_additional_notes)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = {
                softwareKeyboardController.hide()
            }),
            onValueChange = { onAdditionalInfoChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 20.dp)
                .border(
                    border = BorderStroke(
                        width = 0.7.dp,
                        color = colorResource(id = R.color.textColor),
                    ),
                    shape = RoundedCornerShape(7.dp)
                ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = colorResource(id = R.color.textColor),
            )
        )
    }
}


@Composable
@Preview
fun BottomButtons(
    onCancelClick: () -> Unit = {},
    onNextClick: () -> Unit = {},

    ) {
    val localSpacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
    ) {
        Button(
            border = BorderStroke(
                2.dp,
                color = colorResource(id = R.color.textColor)
            ),
            shape = RoundedCornerShape(
                topEnd = 60.dp,
                bottomEnd = 60.dp
            ),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.white)),
            onClick = {
                onCancelClick()
            },
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .offset(x = (-5).dp)
        ) {
            Text(
                text = stringResource(id = R.string.cancel).uppercase(),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 17.sp),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
            )
        }
        Spacer(
            modifier = Modifier
                .padding(localSpacing.spaceSmall)
                .fillMaxHeight()
                .background(color = Color.Black)
        )

        Button(
            border = BorderStroke(
                2.dp,
                color = colorResource(id = R.color.text_dark)
            ),
            shape = RoundedCornerShape(
                topStart = 60.dp,
                bottomStart = 60.dp
            ),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.text_dark)),
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .offset(x = (5).dp)
        ) {
            Text(
                text = stringResource(R.string.string_next).uppercase(),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 17.sp),
                textAlign = TextAlign.Start,
                color = colorResource(id = R.color.white),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp)
            )

        }

    }
}

package com.bjs.pdanative.presentation.screens.lgv_check

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bjs.pdanative.R
import com.bjs.pdanative.domain.models.lgvCheck.LgvCheckInfo
import com.bjs.pdanative.presentation.components.CustomBrush
import com.core_ui.components.buttons.GradientHalfButton


//
// Created by Gaurav Chauhan on 30/05/22.
// Copyright (c) 2022 BJS. All rights reserved.
//

@Composable
fun ShowLgvCheckData(viewModel: LgvCheckViewModel, lgvCheckInfo: LgvCheckInfo, modifier: Modifier) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(brush = CustomBrush.vGradientGrayWhite),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        HeaderIcon(
            false,
            230.dp,
            50.dp,
            R.color.white,
            icon = lgvCheckInfo.checkIcon,
            lgvCheckInfo = lgvCheckInfo,
            position = viewModel.state.selected
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = lgvCheckInfo.checkTitle, color = colorResource(id = R.color.blackSecondary),
            fontWeight = FontWeight.Bold, fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = lgvCheckInfo.checkDescription,
            color = colorResource(id = R.color.text_dark),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp, start = 30.dp, end = 30.dp)
                .align(alignment = Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = "",
                tint = colorResource(id = R.color.bjs),
                modifier = Modifier.padding(start = 10.dp)
            )

            lgvCheckInfo.checkTip?.let { it1 ->
                Text(
                    text = " Tip: $it1",
                    color = colorResource(id = R.color.text_dark_op_65),
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 20.dp, start = 5.dp, end = 30.dp),
                    textAlign = TextAlign.Center
                )
            }


        }
        Spacer(modifier = Modifier.height(60.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GradientHalfButton(
                text = "Fail",
                modifier = Modifier.weight(0.2f, fill = false),
                topRight = 0.dp,
                bottomRight = 0.dp,
                gradient = CustomBrush.hOrangeRedGradient,
                iconVisible = false,
                textColor = colorResource(id = R.color.white), onclick = {
                    viewModel.onEvent(
                        LgvCheckUIEvents.OnFailSelected(
                            position = viewModel.state.selected
                        )
                    )
                }

            )
            Spacer(modifier = Modifier.width(30.dp))
            GradientHalfButton(
                text = "Pass",
                modifier = Modifier.weight(0.2f, fill = false),
                topLeft = 0.dp,
                bottomLeft = 0.dp,
                gradient = CustomBrush.hGradientGreenAndLightGreen,
                iconVisible = false,
                textColor = colorResource(id = R.color.white), onclick = {
                    viewModel.onEvent(
                        LgvCheckUIEvents.OnPassSelected(
                            position = viewModel.state.selected
                        )
                    )
                }
            )
        }
    }


}
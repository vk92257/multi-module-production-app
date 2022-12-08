package com.bjs.pdanative.presentation.screens.lgv_check

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bjs.pdanative.R
import com.bjs.pdanative.domain.models.lgvCheck.LgvCheckInfo
import com.bjs.pdanative.presentation.components.CustomBrush

@Composable
@Preview
fun HeaderIcon(
    dividerVisible: Boolean = false,
    cardSize: Dp = 45.dp,
    imagePadding: Dp = 10.dp,
    color: Int = R.color.blackSecondary,
    icon: Int = R.drawable.ic_launcher_foreground,
    modifier: Modifier = Modifier,
    lgvCheckInfo: LgvCheckInfo?=null,
    position: Int=0
) {
    var backGroundColor = lgvCheckInfo.let {
        if (it!!.pass) {
            CustomBrush.lGradientGreenAndLightGreen
        } else if (it.fail) {
            CustomBrush.vPinkRedGradient
        } else {
            CustomBrush.vGradientGrayUnSelected
        }
    }


    Card(
        shape = CircleShape, modifier = modifier
            .size(cardSize)
            .clip(CircleShape)
            .padding(1.dp),
        backgroundColor = colorResource(id = R.color.light_grey),
        border = BorderStroke(
            width = 0.9.dp,
            color = Color.White,
        ), elevation = 10.dp
    ) {
        Box(modifier = Modifier
            .clip(CircleShape)
            .background(brush = backGroundColor), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(icon),
                contentDescription = "",
                modifier = Modifier.padding(imagePadding).matchParentSize()
            )
        }


    }
    if (dividerVisible) {
        Divider(
            modifier = Modifier
                .height(1.dp)
                .width(20.dp)
                .background(color = colorResource(id = R.color.white))
        )
    }


}
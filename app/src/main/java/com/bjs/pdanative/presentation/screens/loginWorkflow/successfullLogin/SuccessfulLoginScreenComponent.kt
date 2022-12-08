package com.bjs.pdanative.presentation.screens.loginWorkflow.successfullLogin

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

/**
 * @Author: Vivek
 * @Date: 04/04/22
 */
@Preview(widthDp = 200, showBackground = true)
@Composable
fun ProgressBarPreview() {

    val value = 100f
    val modifier = Modifier
    val background = MaterialTheme.colors.background
    val caloriesExceedColor = MaterialTheme.colors.error

    val progressWithAnimation = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = value) {
        progressWithAnimation.animateTo(
            targetValue = value
        )
    }

    Canvas(modifier = modifier) {
            drawRoundRect(
                color = background,
                size = size,
                cornerRadius = CornerRadius(100f)
            )

            drawRoundRect(
                color = Color.Yellow,
                size = Size(
                    width = value,
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)
            )


/*
        } else {
            drawRoundRect(
                color = caloriesExceedColor,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
        }*/
    }


    /* Row(
         modifier = Modifier
             .height(4.dp)
             .clip(RoundedCornerShape(50, 50, 50, 50)) // (1)
             .background(Color.White.copy(alpha = 0.4f)) // (2)
     ) {
         Box(
             modifier = Modifier
                 .background(Color.White)
                 .fillMaxHeight()
                 .fillMaxWidth(0.5f), // (3)
         ) {}
     }*/
}

package com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.logDamageScreen.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean

@Composable
@Preview
fun LogDamageHeaderItem() {
    val localSpacing = LocalSpacing.current
    Spacer(modifier = Modifier.size(localSpacing.spaceMedium))
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
            border = BorderStroke(
                width = 2.dp,
                color = Color.White,
            ), elevation = 10.dp
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_exterior),
                tint = Color.White,
                contentDescription = "",
                modifier = Modifier
                    .drawBehind {
                        drawCircle(brush = CustomBrush.hOrangeRedGradient)
                    }
                    .padding(15.dp)
            )

        }

        Text(
            text = "Exterior",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 15.dp),
            color = colorResource(id = R.color.black),
            maxLines = 1,
            style = TypographyEvelethClean.h6,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
        )


        Text(
            text = "Summary of logged damage/faults.",
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            color = colorResource(id = R.color.black),
            maxLines = 1,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.size(localSpacing.spaceMedium))

    }

}
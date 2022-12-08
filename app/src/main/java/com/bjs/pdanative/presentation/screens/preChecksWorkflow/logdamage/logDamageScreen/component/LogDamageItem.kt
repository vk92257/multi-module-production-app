package com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.logDamageScreen.component


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.core_ui.components.buttons.GradientButton
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean

@Composable
@Preview
fun LogDamageItem(
    damagedComponent: String = "Wing Mirror",
    priority: String = "Urgent",
    notes: String = "Mirror glass is cracked",
    index: Int = 0,
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    val localSpacing = LocalSpacing.current

    Column(
        Modifier.padding(horizontal = 20.dp)
    ) {
        Card(
            elevation = 15.dp,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = colorResource(id = R.color.sugar_cane),
            modifier = Modifier.padding(5.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .background(color = colorResource(id = R.color.sugar_cane))
            ) {
                Box(
                    modifier = Modifier
                        .background(color = colorResource(id = R.color.green_white))
                        .height(60.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_wrench),
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterStart),
                        contentDescription = "wrench"
                    )
                    Text(
                        text = stringResource(R.string.vehicle_damage),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(horizontal = 30.dp),
                        color = colorResource(id = R.color.black),
                        maxLines = 1,
                        style = TypographyEvelethClean.body1,
                        textAlign = TextAlign.Center,
                    )


                    Box(
                        Modifier
                            .size(35.dp)
                            .background(colorResource(id = R.color.black), shape = CircleShape)
                            .align(Alignment.CenterEnd),
                    ) {
                        Text(
                            text = (index + 1).toString().take(2), maxLines = 1,
                            modifier = Modifier
                                .align(Alignment.Center),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.white),
                        )
                    }
                }
                Spacer(modifier = Modifier.size(localSpacing.spaceTwelve))

                Column(Modifier.padding(20.dp)) {


                    DamageItem(
                        title = stringResource(R.string.damage_component),
                        value = damagedComponent
                    )
                    Spacer(modifier = Modifier.size(localSpacing.spaceTwentyTwo))
                    DamageItem(
                        title = stringResource(R.string.priority),
                        value = priority,
                    )
                    Spacer(modifier = Modifier.size(localSpacing.spaceTwentyTwo))
                    DamageItem(
                        title = stringResource(R.string.additional_info),
                        value = notes,
                    )

                    Spacer(modifier = Modifier.size(localSpacing.spaceLarge))

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
                                60.dp
                            ),
                            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.white)),
                            onClick = {
                                onEditClick()
                            },
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        ) {
                            Text(
                                text = stringResource(id = R.string.edit).uppercase(),
                                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 17.sp),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .padding(localSpacing.spaceSmall)
                                .fillMaxHeight()
                                .background(color = Color.Black)
                        )
                        GradientButton(
                            text = stringResource(id = R.string.delete).uppercase(),
                            textColor = Color.White,
                            buttonModifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .weight(1f),
                            gradient = CustomBrush.vPinkRedGradient,
                            iconVisible = false,
                            onclick = {
                                onDeleteClick()
                            }
                        )

                    }

                }

            }

        }

    }


}


@Composable
fun DamageItem(
    title: String,
    value: String,
) {
    val localSpacing = LocalSpacing.current
    Text(
        text = title,
        color = colorResource(id = R.color.shooner),
        maxLines = 1,
        style = MaterialTheme.typography.body1,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
    )
    Spacer(modifier = Modifier.size(localSpacing.spaceSmall))
    Divider(
        color = colorResource(id = R.color.bjs),
        modifier = Modifier
            .width(40.dp)
            .height(3.dp)
    )
    Spacer(modifier = Modifier.size(localSpacing.spaceTwelve))
    Text(
        text = value,
        color = colorResource(id = R.color.black),
        style = MaterialTheme.typography.body1,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start,
    )
}
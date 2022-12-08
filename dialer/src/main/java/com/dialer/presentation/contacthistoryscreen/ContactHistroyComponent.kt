package com.dialer.presentation.contacthistoryscreen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.dialer.R
import com.dialer.domain.model.CallLogsPojo
import com.dialer.domain.model.isCustomEmpty
import com.dialer.util.CallType


@Preview
@Composable
fun ContactHistoryItem(
    modifier: Modifier = Modifier,
    callLogPojo: CallLogsPojo = CallLogsPojo(),
) {
    val space = LocalSpacing.current
    var callTypeColor: Color = Color.Green
    var callTypeIcon: Painter = painterResource(id = R.drawable.incoming_call)
    when (callLogPojo.type) {
        CallType.INCOMING -> {
            callTypeIcon = painterResource(id = R.drawable.incoming_call)
            callTypeColor = colorResource(id = R.color.green)
        }
        CallType.OUTGOING -> {
            callTypeIcon = painterResource(id = R.drawable.outgoing_call)
            callTypeColor = Color.Gray
        }
        CallType.MISSED -> {
            callTypeIcon = painterResource(id = R.drawable.miss_call)
            callTypeColor = Color.Red
        }

        CallType.REJECTED -> {
            callTypeIcon = painterResource(id = R.drawable.rejected)
            callTypeColor = Color.Red
        }

        CallType.UNKNOWN -> {
            callTypeIcon = painterResource(id = R.drawable.rejected)
            callTypeColor = Color.Red
        }

        CallType.CALLING -> {}

        else -> {}
    }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(
                100.dp
            )
            .background(
                color = colorResource(id = R.color.white),
            ),
        /*  verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween*/
    ) {
        Row {
            Text(
                text = callLogPojo.date, color = colorResource(id = R.color.blackSecondary).copy(
                    alpha = 0.7f
                ), style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold
            )// Text}
            Spacer(modifier = Modifier.size(10.dp))
        }

        Spacer(modifier = Modifier.size(space.spaceSmall))
        Text(
            text = callLogPojo.time, color = colorResource(id = R.color.blackSecondary).copy(
                alpha = 0.7f
            ), style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold
        )// Text}

        Spacer(modifier = Modifier.size(space.spaceSmall))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Text(
                    text = callLogPojo.type.toString(),
                    color = colorResource(id = R.color.blackSecondary).copy(
                        alpha = 0.6f
                    ),
                    style = MaterialTheme.typography.body1, fontWeight = FontWeight.Normal,
                ) // Text
                Spacer(modifier = Modifier.size(space.spaceMedium))
                Icon(
                    painter = callTypeIcon, contentDescription = "phone", modifier = Modifier
                        .align(
                            alignment = Alignment.CenterVertically
                        )
                        .size(
                            size = 15.dp,
                        )
                        .padding(), tint = callTypeColor
                ) // Icon
            } // Row}


            Text(
                text = callLogPojo.duration,
                color = colorResource(id = R.color.blackSecondary).copy(
                    alpha = 0.6f
                ),
                style = MaterialTheme.typography.body1, fontWeight = FontWeight.Normal,
            ) // Text

        } // Column
    }
}


@Composable
@Preview
fun HistoryTitle(
    modifier: Modifier = Modifier,
    callLogPojo: CallLogsPojo = CallLogsPojo(),
    onBackClick: () -> Unit = {},
    call: (String) -> Unit = {}

) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "back_arrow",
            modifier = Modifier.clickable {
                onBackClick()
            })   // Icon

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = callLogPojo.carrierReference.isCustomEmpty(callLogPojo.number),
                    color = colorResource(id = R.color.blackSecondary).copy(
                        alpha = 0.7f
                    ),
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )// Text

                if (callLogPojo.carrierReference.isNotEmpty()) Text(
                    text = " Drop ${callLogPojo.dropNumber}",
                    color = colorResource(id = R.color.blackSecondary).copy(
                        alpha = 0.7f
                    ),
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Normal
                )// Text}

                Spacer(modifier = Modifier.size(10.dp))
            }

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = callLogPojo.date, color = colorResource(id = R.color.blackSecondary).copy(
                    alpha = 0.7f
                ), style = MaterialTheme.typography.body2, fontWeight = FontWeight.Normal
            )// Text}
        }


        IconButton(
            onClick = {
                call(callLogPojo.number)
            }, modifier = Modifier
                .size(
                    width = 40.dp, height = 40.dp
                )
                .shadow(
                    elevation = 10.dp, shape = RoundedCornerShape(10.dp)
                )
                .background(
                    color = colorResource(id = R.color.green), shape = RoundedCornerShape(10.dp)
                )
        ) {
            Icon(painter = painterResource(id = R.drawable.phone),
                contentDescription = "",
                tint = colorResource(id = R.color.white),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .clickable {
                        call(callLogPojo.number)
                    })
        } //IconButton
    } //IconButton


}

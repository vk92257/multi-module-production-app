package com.dialer.presentation.calllog


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.dialer.presentation.contact.DropCount
import com.dialer.util.CallType
import com.dialer.util.ContactType


@Preview
@Composable
fun CallLogView(
    modifier: Modifier = Modifier,
    callLogPojo: CallLogsPojo = CallLogsPojo(),
) {

    val space = LocalSpacing.current


    var contactTypeColor: Color = colorResource(id = R.color.blue)
    var contactTypeIcon: Painter = painterResource(id = R.drawable.ic_homepage)
    when (callLogPojo.contactType) {
        ContactType.HOME -> {
            contactTypeColor = colorResource(id = R.color.blue)
            contactTypeIcon = painterResource(id = R.drawable.ic_homepage)
        }
        ContactType.OFFICE -> {
            contactTypeColor = colorResource(id = R.color.green)
            contactTypeIcon = painterResource(id = R.drawable.ic_office)
        }
        ContactType.MOBILE -> {
            contactTypeColor = colorResource(id = R.color.dark_grayish_orange)
            contactTypeIcon = painterResource(id = R.drawable.ic_mobile_phone)
        }

        ContactType.OTHER -> {
//            contactTypeColor = Color.Red
//            contactTypeIcon = painterResource(id = R.drawable.outgoing_call)
        }
        ContactType.UNKNOWN -> {

        }

    }


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

        CallType.CALLING -> {

        }

        else -> {}
    }


    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(
                100.dp
            )
            .background(
                color = colorResource(id = R.color.white),
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(
                painter = callTypeIcon, contentDescription = "phone", modifier = Modifier
                    .align(
                        alignment = Alignment.CenterVertically
                    )
                    .size(
                        size = 22.dp,
                    )
                    .padding(), tint = callTypeColor
            ) // Icon
            Spacer(modifier = Modifier.size(space.spaceMedium))
            Row {
                DropCount(
                    modifier = Modifier.align(
                        alignment = Alignment.CenterVertically
                    ),
                    count = if (callLogPojo.dropNumber != "500") callLogPojo.dropNumber else callLogPojo.name.isCustomEmpty(
                        "Unknown"
                    ).take(1),
                    hideDrop = (callLogPojo.dropNumber == "500")
                ) // DropCount
                Spacer(
                    modifier = Modifier.width(15.dp)
                )
                Column {
                    Spacer(modifier = Modifier.size(10.dp))
                    Row {
                        Text(
                            text = callLogPojo.postcode.isCustomEmpty(callLogPojo.number),
                            color = colorResource(id = R.color.blackSecondary),
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold
                        )// Text

                        Spacer(modifier = Modifier.size(10.dp))

                        Icon(
                            painter = contactTypeIcon,
                            contentDescription = "phone",
                            modifier = Modifier
                                .align(
                                    alignment = Alignment.CenterVertically
                                )
                                .size(
                                    size = 22.dp,
                                )
                                .padding(),
                            tint = contactTypeColor
                        ) // Icon

                    } // Row


                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = callLogPojo.name.isCustomEmpty("Unknown"),
                        color = colorResource(id = R.color.blackSecondary).copy(
                            alpha = 0.7f
                        ),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold
                    )// Text

                    Spacer(modifier = Modifier.height(9.dp))
                    Text(
                        text = callLogPojo.carrierReference,
                        color = colorResource(id = R.color.blackSecondary).copy(
                            alpha = 0.5f
                        ),
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.Normal
                    ) // Text

                    Spacer(modifier = Modifier.size(10.dp))
                } // Column
            } // Row
        } // Row
        Column {


            Text(
                text = callLogPojo.callHistoryCount.toString(),
                color = colorResource(id = R.color.blackSecondary).copy(
                    alpha = 0.9f
                ),
                style = MaterialTheme.typography.body1, fontWeight = FontWeight.Bold,
            ) // Text


            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = callLogPojo.time,
                color = colorResource(id = R.color.blackSecondary).copy(
                    alpha = 0.3f
                ),
                style = MaterialTheme.typography.body1, fontWeight = FontWeight.Bold,
            ) // Text


        }

    } // Row
}


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
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = callLogPojo.time, color = colorResource(id = R.color.blackSecondary).copy(
                alpha = 0.7f
            ), style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold
        )// Text
        Spacer(modifier = Modifier.size(10.dp))

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


@Preview
@Composable
fun StickyHeader(modifier: Modifier = Modifier, date: String = "Today") {
    Row(
        modifier = modifier
            .background(
                color = colorResource(
                    id = R.color.white,
                )
            )
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = date,
            modifier = Modifier.padding(
                vertical = 10.dp, horizontal = 35.dp
            ),
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.pink)
        )


        /*Text(
            text = "See all..",
            style = MaterialTheme.typography.body1,
            color = colorResource(id = com.ui.R.color.green),
            modifier = Modifier.align(
                alignment = Alignment.Bottom
            )
        )*/
    }
}


/*fun main() {
    print("Hello World")
    var data: String? = "null"
    data = null
    data?.let {
        print("Data is not null")
    } ?: run { print("Data is null") }
}*/


@Preview
@Composable
fun CallLogItem(callLogPojo: CallLogsPojo = CallLogsPojo(), onClick:()->Unit = {}) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        CallLogView(modifier = Modifier
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 20.dp
            ), callLogPojo


            /*ContactHistoryItem(modifier = Modifier
                .clickable {

                }
                .padding(
                    horizontal = 20.dp
                )*/

        )
    }

}













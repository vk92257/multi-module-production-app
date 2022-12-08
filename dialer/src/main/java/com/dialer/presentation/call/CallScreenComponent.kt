package com.dialer.presentation.call

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.dialer.R
import com.dialer.customview.OnTwowaySliderListener
import com.dialer.customview.TwowaySliderView
import com.dialer.domain.model.CallPojo
import com.dialer.util.ContactType


@Preview
@Composable
fun CallCallerInfo(
    modifier: Modifier = Modifier, callPojo: CallPojo = CallPojo()
) {


    val spacer = LocalSpacing.current

    var contactTypeColor: Color = colorResource(id = R.color.blue)
    var contactTypeIcon: Painter = painterResource(id = R.drawable.ic_homepage)
    when (callPojo.contactType) {
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
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.veryDarkGray)
            )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = callPojo.type.toString(),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.bjs)
            )


            Text(
                text = callPojo.time,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.bjs)
            )

        }

        Spacer(modifier = Modifier.size(spacer.spaceTen))
        Text(
            text = "Drop ${callPojo.dropNumber}",
            style = MaterialTheme.typography.subtitle1,
            color = colorResource(id = R.color.white)
        )
        Spacer(modifier = Modifier.size(spacer.spaceTen))
        Text(
            text = callPojo.postcode,
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white)
        )
        Row {
            Text(
                text = callPojo.name,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.white)
            )

            Spacer(modifier = Modifier.size(spacer.spaceTen))
            Icon(
                painter = contactTypeIcon,
                contentDescription = null,
                tint = contactTypeColor,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically)
            )
        }

        Spacer(modifier = Modifier.size(spacer.spaceTen))
        Text(
            text = callPojo.carrierReference,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white)
        )


    }

}


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun CallSlider(
    modifier: Modifier = Modifier,
    onSlideLeft: () -> Unit,
    onSlideRight: () -> Unit,
) {
    val context = LocalContext.current
    val callSlider = TwowaySliderView(context)


    with(callSlider) {
        bgdColor = R.color.transparent
        sliderColor = R.color.bjs
        fillCircle = false
        sliderImage = R.drawable.phone
        setBgColor(context.getColor(R.color.transparent))


        callSlider.listener = object : OnTwowaySliderListener {
            override fun onSliderMoveLeft() {
                onSlideLeft()
            }

            override fun onSliderMoveRight() {
                onSlideRight()
            }

            override fun onSliderLongPress() {
            }

        }
    }

    AndroidView(
        factory = { callSlider }, modifier = modifier
            .fillMaxWidth()
            .height(
                height = 100.dp
            )
            .background(
                color = Color.Transparent
            )
    )

}


@Preview
@Composable
fun CallControlButtons(
    modifier: Modifier = Modifier,
    callPojo: CallPojo = CallPojo(),
    onMuteClick: () -> Unit = {},
    onHoldClick: () -> Unit = {},
    onAddCallClick: () -> Unit = {},
    onSpeakerClick: () -> Unit = {},
    onSwapCallClick: () -> Unit = {},
    onNumPadClick: () -> Unit = {},
    callEndClick: () -> Unit = {}
) {


    Column(
        modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {

//            Mike Buttons

            CallButtons(
                enabledIcon = painterResource(id = R.drawable.microphone_enable),
                disabledIcon = painterResource(id = R.drawable.microphone_disable),
                isEnabled = callPojo.isMute,
                onCallClick = onMuteClick
            )


            //       Hold Buttons

            CallButtons(
                enabledIcon = painterResource(id = R.drawable.ic_hold),
                disabledIcon = painterResource(id = R.drawable.ic_unhold),
                isEnabled = callPojo.isHoldPressed,
                onCallClick = onHoldClick
            )

//            Speaker Buttons
            CallButtons(
                enabledIcon = painterResource(id = R.drawable.volume_high),
                disabledIcon = painterResource(id = R.drawable.mute),
                isEnabled = callPojo.isOnSpeaker,
                onCallClick = onSpeakerClick
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {


//            Open Dial Pad Buttons
            CallButtons(
                enabledIcon = painterResource(id = R.drawable.ic_dialpad),
                disabledIcon = painterResource(id = R.drawable.ic_dialpad),
                onCallClick = onNumPadClick
            )

            //            Open Add Call  Buttons
            CallButtons(
                enabledIcon = painterResource(id = R.drawable.ic_add_call),
                disabledIcon = painterResource(id = R.drawable.ic_add_call),
                onCallClick = onAddCallClick
            )


//            Swap Calls Buttons
            if (callPojo.showHoldView) CallButtons(
                enabledIcon = painterResource(id = R.drawable.ic_swap),
                disabledIcon = painterResource(id = R.drawable.ic_swap),
                onCallClick = onSwapCallClick
            )
        }

        Spacer(modifier = Modifier.size(50.dp))
        IconButton(
            onClick = {
                callEndClick()
            }, modifier = Modifier
                .size(
                    width = 140.dp, height = 70.dp
                )
                .shadow(
                    elevation = 10.dp, shape = RoundedCornerShape(10.dp)
                )
                .background(
                    color = colorResource(id = R.color.red), shape = RoundedCornerShape(10.dp)
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.phone),
                contentDescription = "",
                tint = colorResource(id = R.color.white),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            )
        }

    }

}


//@Preview
@Composable
fun CallButtons(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    onCallClick: () -> Unit = {},
    enabledIcon: Painter = painterResource(id = R.drawable.ic_hold),
    disabledIcon: Painter = painterResource(id = R.drawable.ic_unhold),
    enabledColor: Color = colorResource(id = R.color.white),
    disabledColor: Color = colorResource(id = R.color.bjs),
) {
    IconButton(
        onClick = onCallClick, modifier = modifier
            .size(70.dp)
            .shadow(
                elevation = 10.dp, shape = CircleShape
            )
            .background(
                color = colorResource(id = R.color.text_dark), shape = CircleShape
            )
    ) {
        Icon(
            painter = if (isEnabled) disabledIcon else enabledIcon,
            contentDescription = "",
            tint = enabledColor,
            modifier = modifier
                .fillMaxSize()
                .padding(21.dp)
        )
    }


}

@Preview
@Composable
fun HoldView(
    modifier: Modifier = Modifier,
    callPojo: CallPojo = CallPojo(),
    onDisconnectClick: (String) -> Unit = {},
) {

    val spacer = LocalSpacing.current

    var contactTypeColor: Color = colorResource(id = R.color.blue)
    var contactTypeIcon: Painter = painterResource(id = R.drawable.ic_homepage)
    when (callPojo.contactType) {
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


    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(
                60.dp,
            )
            .background(
                color = colorResource(id = R.color.black).copy(
                    alpha = 0.5f
                ),
            ),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "on Hold",
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.bjs),
            modifier = Modifier
        ) // Text

        Row {
            Text(
                text = callPojo.postcode,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.white),
                modifier = Modifier
            ) // Text

            Spacer(modifier = Modifier.size(spacer.spaceSmall))

            Icon(
                painter = contactTypeIcon, contentDescription = "phone", modifier = Modifier
                    .align(
                        alignment = Alignment.CenterVertically
                    )
                    .size(
                        size = 22.dp,
                    )
                    .padding(), tint = contactTypeColor
            ) // Icon


        } // Row

        Icon(painter = painterResource(id = R.drawable.ic_phone_hangup),
            contentDescription = "phone",
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterVertically
                )
                .size(
                    size = 32.dp,
                )
                .clickable {
                    onDisconnectClick(callPojo.number)
                }
                .padding(),
            tint = colorResource(id = R.color.red)

        ) // Icon


    } // Row

}






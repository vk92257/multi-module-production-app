package com.dialer.presentation.contact

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.core_ui.removeLast
import com.dialer.R


@Composable
@Preview
fun DialPad(
    modifier: Modifier = Modifier,
    dialPadList: Array<String> = stringArrayResource(id = R.array.dial_pad_numbers),
    onCallClick: (String) -> Unit = {}
) {

    val spacer = LocalSpacing.current
    val dialPadListArray by remember {
        mutableStateOf(dialPadList)

    }

    val context = LocalContext.current

    val phoneNumber = remember {
        mutableStateOf("")
    }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.blackSecondary), shape = RoundedCornerShape(8.dp)
            )
    ) {

        Spacer(modifier = Modifier.size(spacer.spaceLarge))
        DialPadNumberScreen(
            modifier = Modifier.padding(
                horizontal = 40.dp
            ),
            phoneNumber = phoneNumber.value,
            onClearClick = {
                phoneNumber.value = phoneNumber.value.removeLast()
            },
        )
        Spacer(modifier = Modifier.size(spacer.spaceLarge))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3), modifier = Modifier
                .background(
                    color = Color.Transparent
                )
                .padding(
                    horizontal = 30.dp
                )
        ) {
            itemsIndexed(dialPadListArray) { index, item ->
                DialPadButton(onButtonClick = {
//                    onButtonClick(item)
                    phoneNumber.value = phoneNumber.value + (item)
//                    makeToast(context = context, message = phoneNumber.toString())
                }, item = item)
            }
        }  // LazyVerticalGrid

        Spacer(modifier = Modifier.size(spacer.spaceLarge))


        IconButton(
            onClick = {
                onCallClick(phoneNumber.value)
            }, modifier = Modifier
                .size(
                    width = 140.dp, height = 70.dp
                )
                .shadow(
                    elevation = 10.dp, shape = RoundedCornerShape(10.dp)
                )
                .background(
                    color = colorResource(id = R.color.green), shape = RoundedCornerShape(10.dp)
                )
                .align(
                    alignment = Alignment.CenterHorizontally
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
        } //IconButton

        Spacer(modifier = Modifier.size(spacer.spaceLarge))

    } // Column


}

@Composable
fun DialPadButton(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    item: String = "1",
) {
    Button(onClick = {
        onButtonClick()
    }, colors = ButtonDefaults.buttonColors(
        backgroundColor = Color.Transparent, contentColor = Color.Transparent

    ), elevation = null, modifier = modifier
        .size(80.dp)
        .clickable {

        }) {
        Text(
            text = item,
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            /*modifier = Modifier.clickable {
                onButtonClick()
            }*/
        )
    }
}

//@Preview
@Composable
fun DialPadNumberScreen(
    modifier: Modifier = Modifier,
    phoneNumber: String = "1234567890",
    onClearClick: () -> Unit = {},
) {


    var phoneNumber1 by remember { mutableStateOf("") }
    val context = LocalContext.current

    val tailIcon = @Composable {
        Icon(painter = painterResource(id = R.drawable.ic_clear),
            contentDescription = "Clear",
            tint = Color.White.copy(
                alpha = if (phoneNumber.isEmpty()) 0.3f else 0.9f
            ),
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    onClearClick()
                })
    }


    val focusManager = LocalFocusManager.current

    CompositionLocalProvider(
        LocalTextInputService provides null
    ) {
        TextField(
            value = phoneNumber,
            maxLines = 1,

            onValueChange = {
//            state = it
                phoneNumber1 = it
            },
            singleLine = true,
            trailingIcon = tailIcon,
            modifier = modifier
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(7.dp),
                )
                .fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.White,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Left)
            }

            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(id = R.color.white),
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(7.dp),
        )
    }


}


//@Preview
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchClick: (String) -> Unit = {},
) {
    var state by remember { mutableStateOf("") }
    val leadingIconView = @Composable {
        IconButton(
            onClick = {

            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                modifier = Modifier
                    .size(20.dp)
                    .alpha(0.5f)
            )
        }
    }

    val trailingIconView = @Composable {
        IconButton(
            onClick = {

            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.contacts),
                contentDescription = "filter",
                modifier = Modifier
                    .size(20.dp)
                    .alpha(0.5f)
            )
        }
    }


    TextField(
        value = state,

        onValueChange = {
            state = it
            onSearchClick(state)
        },
        modifier = modifier
            .shadow(
                elevation = 5.dp, shape = RoundedCornerShape(7.dp)
            )

            .background(
                color = colorResource(id = R.color.semi_gray),
                shape = RoundedCornerShape(7.dp),
            )
            .fillMaxWidth()
            .height(50.dp),
//        trailingIcon = trailingIconView,
        leadingIcon = leadingIconView,

        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = "Search",
                textAlign = TextAlign.Center,
                color = Color.Gray,
            )
        },
        shape = RoundedCornerShape(7.dp),
    )


}


//@Preview
@Composable
fun DropCount(
    modifier: Modifier = Modifier, count: String = "23", hideDrop: Boolean = false

) {
    Column(
        modifier = modifier
            .shadow(
                shape = CircleShape,
                elevation = 5.dp,
            )

            .size(
                70.dp
            )
            .background(
                color = colorResource(id = R.color.blackSecondary), shape = CircleShape
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count,
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.h6,
        )

        Spacer(modifier = Modifier.size(7.dp))

        if (!hideDrop) Text(
            text = "Drop",
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold
        )


    }


}


//@Preview
@Composable
fun ContactBottomView(
    modifier: Modifier = Modifier,
    name: String = "Cameron Murray",
    carrierReference: String = "BR0948-947547",
    isOrder: Boolean = false,
    contactHome: String = "",
    contactWork: String = "",
    contactPhone: String = "",
    onCallClick: (String) -> Unit = {}

) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                color = colorResource(id = R.color.bg_light_white),
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Column {
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = name, color = colorResource(id = R.color.blackSecondary).copy(
                    alpha = 0.7f
                ), style = MaterialTheme.typography.body1, fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(9.dp))
            Text(
                text = carrierReference, color = colorResource(id = R.color.blackSecondary).copy(
                    alpha = 0.5f
                ), style = MaterialTheme.typography.body2, fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.size(10.dp))
        } // Column

        if (contactWork.isNotEmpty()) Icon(painter = painterResource(id = R.drawable.ic_office),
            contentDescription = "phone",
            modifier = Modifier
                .size(
                    size = 45.dp,
                )
                .clickable {
                    onCallClick(contactWork)
                }
                .shadow(
                    elevation = 5.dp, shape = RoundedCornerShape(5.dp)
                )
                .background(
                    color = colorResource(id = R.color.green), shape = RoundedCornerShape(5.dp)
                )
                .padding(12.dp),
            tint = Color.White


        ) // Icon

        if (contactHome.isNotEmpty()) Icon(painter = painterResource(id = R.drawable.ic_homepage),
            contentDescription = "phone",
            modifier = Modifier
                .size(
                    size = 45.dp,
                )
                .clickable {
                    onCallClick(contactHome)
                }
                .shadow(
                    elevation = 5.dp, shape = RoundedCornerShape(5.dp)
                )
                .background(
                    color = colorResource(id = R.color.blue), shape = RoundedCornerShape(5.dp)
                )
                .padding(12.dp),
            tint = Color.White


        ) // Icon

        if (contactPhone.isNotEmpty()) Icon(painter = painterResource(id = R.drawable.ic_mobile_phone),
            contentDescription = "phone",
            modifier = Modifier
                .size(
                    size = 45.dp,
                )
                .clickable {
                    onCallClick(contactPhone)
                }
                .shadow(
                    elevation = 5.dp, shape = RoundedCornerShape(5.dp)
                )
                .background(
                    color = colorResource(id = R.color.dark_grayish_orange),
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(12.dp),
            tint = Color.White) // Icon
    } // Row
}


//@Preview
@Composable
fun ContactTopView(
    modifier: Modifier = Modifier,
    dropCount: String = "500",
    postCode: String = "BGR TGR",
    name: String = "Cameron Murray",
    carrierReference: String = "BR0948-947547",
    isOrder: Boolean = false,
    number: String = "",
    onCallClick: (String) -> Unit = {}
) {
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
            DropCount(
                modifier = Modifier.align(
                    alignment = Alignment.CenterVertically
                ), count = dropCount
            )
            Spacer(
                modifier = Modifier.width(15.dp)
            )
            Column {
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = postCode,
                    color = colorResource(id = R.color.blackSecondary),
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = name, color = colorResource(id = R.color.blackSecondary).copy(
                        alpha = 0.7f
                    ), style = MaterialTheme.typography.body1, fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = carrierReference,
                    color = colorResource(id = R.color.blackSecondary).copy(
                        alpha = 0.5f
                    ),
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.size(10.dp))
            } // Column

        } // Row

        if (!isOrder) Icon(painter = painterResource(id = R.drawable.phone),
            contentDescription = "phone",
            modifier = Modifier
                .size(
                    size = 50.dp,
                )
                .clickable {
                    onCallClick(number)
                }
                .shadow(
                    elevation = 5.dp, shape = RoundedCornerShape(5.dp)
                )
                .background(
                    color = colorResource(id = R.color.green), shape = RoundedCornerShape(5.dp)
                )
                .padding(12.dp),
            tint = Color.White


        ) // Icon

    } // Row
}


@Preview
@Composable
fun ContactItem(
    dropCount: String = "500",
    postCode: String = "BGR TGR",
    name: String = "Cameron Murray",
    carrierReference: String = "BR0948-947547",
    isOrder: Boolean = false,
    number: String = "",
    contactHome: String = "",
    contactWork: String = "",
    contactPhone: String = "",
    onCallClick: (String) -> Unit = {}


) {

    var visible by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ContactTopView(
            modifier = Modifier
                .clickable {
                    visible = !visible
                }
                .padding(
                    horizontal = 20.dp,
                ),
            onCallClick = {
                onCallClick(it)
            },
            name = name,
            carrierReference = carrierReference,
            dropCount = dropCount,
            isOrder = isOrder,
            number = number,
            postCode = postCode,
        )


        if (isOrder) AnimatedVisibility(
            visible = visible, enter = fadeIn(
                // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                initialAlpha = 0.4f
            ), exit = fadeOut(
                // Overwrites the default animation with tween
                animationSpec = tween(durationMillis = 150)
            )
        ) {
            // Content that needs to appear/disappear goes here:
            ContactBottomView(
                modifier = Modifier,
                onCallClick = {
                    onCallClick(it)
                },
                isOrder = isOrder,
                carrierReference = carrierReference,
                name = name,
                contactHome = contactHome,
                contactPhone = contactPhone,
                contactWork = contactWork,

                )
        }
    }

}













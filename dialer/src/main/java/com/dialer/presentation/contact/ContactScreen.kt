package com.dialer.presentation.contact

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.core_ui.components.header.TitleHeader
import com.dialer.R
import com.dialer.util.makeCall
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.M)
@Preview
@Composable
fun ContactScreen(
    viewModel: ContactsViewModel = hiltViewModel(), showDialPad: Boolean = false
) {
    val state = viewModel.state
    val context = LocalContext.current
    BottomSheet() { padding, openCloseBottomSheet ->
        Scaffold(modifier = Modifier
            .fillMaxSize()
            .padding(padding),
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                ExtendedFloatingButton(
                    onClick = {
                        openCloseBottomSheet()
                    },
                    modifier = Modifier,
                )
                Spacer(modifier = Modifier.height(150.dp))
            },
            content = { paddingIn ->
                Column(
                    modifier = Modifier
                        .padding(paddingIn)
                        .background(color = colorResource(id = R.color.white))
                ) {
                    TitleHeader(
                        title = "Contact List",
                        isShowTrailerIcon = false,
                        isShowHeadIcon = false,
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    SearchBar(modifier = Modifier.padding(
                        horizontal = 15.dp
                    ), onSearchClick = {
                        viewModel.onContactEvent(ContactScreenEvent.OnSearchEvent(it))
                    })
                    Spacer(modifier = Modifier.size(10.dp))
                    LazyColumn() {
                        itemsIndexed(state) { _, item ->
                            ContactItem(dropCount = item.dropNumber,
                                postCode = item.postcode,
                                name = item.name,
                                number = item.number,
                                carrierReference = item.carrierReference,
                                isOrder = item.isOrder,
                                contactHome = item.contactHome,
                                contactWork = item.contactWork,
                                contactPhone = item.contactPhone,
                                onCallClick = {
                                    makeCall(
                                        context = context, onError = {}, phoneNumber = it
                                    )
                                })
                        }
                        item {
                            Spacer(modifier = Modifier.size(80.dp))
                        }
                    }

                }
            })
    }

}


@RequiresApi(Build.VERSION_CODES.M)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    expandDailPad: Boolean = false,
    content: @Composable (PaddingValues, () -> Unit) -> Unit,
) {


    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(if (expandDailPad) BottomSheetValue.Expanded else BottomSheetValue.Collapsed)
    )


    BottomSheetScaffold(
        modifier = Modifier.padding(),
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            DialPad {
                makeCall(it, context) {
                    Log.e("TAG", "BottomSheet: $it")
                }
            }
        },
        sheetPeekHeight = 20.dp,
        floatingActionButtonPosition = FabPosition.Center,
    ) { padding ->
        content(padding) {
            scope.launch {
                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) bottomSheetScaffoldState.bottomSheetState.expand()
                else bottomSheetScaffoldState.bottomSheetState.collapse()
            }
        }
    }
}


@Composable
fun ExtendedFloatingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,

    ) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = colorResource(id = R.color.blackSecondary),
        contentColor = Color.White,
        shape = RoundedCornerShape(9.dp),
        modifier = modifier.size(60.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_dialpad),
            contentDescription = "call",
            modifier = Modifier.size(30.dp)

        )
    }
}


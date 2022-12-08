package com.dialer.presentation.contacthistoryscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dialer.R
import com.dialer.domain.model.CallLogsPojo
import com.dialer.util.makeCall

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContactHistoryScreen(
    viewModel: ContactHistoryViewModel = hiltViewModel(),
    callLogEntity: CallLogsPojo = CallLogsPojo(),
    onBackPress: () -> Unit = {}
) {


    val context = LocalContext.current

    LaunchedEffect(key1 = Unit, block = {
        viewModel.updateCallLogPojo(callLogEntity)
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
    ) {
        HistoryTitle(
            callLogPojo = callLogEntity, onBackClick = onBackPress, call = {
                makeCall(
                    context = context, onError = {}, phoneNumber = it
                )
            }, modifier = Modifier.padding(
                vertical = 20.dp,
                horizontal = 15.dp,
            )
        )
        Divider(
            color = colorResource(id = R.color.blackSecondary).copy(
                alpha = 0.4f
            ),
            thickness = (0.5).dp,
        )


        LazyColumn(state = rememberLazyListState()) {
            itemsIndexed(viewModel.state.contacts) { _: Int, item: CallLogsPojo ->
                ContactHistoryItem(
                    callLogPojo = item, modifier = Modifier.padding(15.dp)
                )
            }
        }
    }
}
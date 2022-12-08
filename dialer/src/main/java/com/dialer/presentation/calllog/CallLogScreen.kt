package com.dialer.presentation.calllog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.core_ui.components.header.TitleHeader
import com.dialer.R
import com.dialer.domain.model.toJsonString

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CallLogScreen(
    viewModel: CallLogsViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
    ) {
        TitleHeader(
            title = "Call Log List",
            isShowTrailerIcon = false,
            isShowHeadIcon = false,
        )
        LazyColumn(state = rememberLazyListState()) {
            viewModel.state.callLogs.forEach { (date, articleForDate) ->
                stickyHeader {
                    StickyHeader(date = date.split("/")[0])
                }
                itemsIndexed(articleForDate) { index, item ->
                    CallLogItem(callLogPojo = item, onClick = {
                        navController.navigate("contact_history/${item.toJsonString()}")
                    })
                }

            }
        }

        val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

        // If `lifecycleOwner` changes, dispose and reset the effect
        DisposableEffect(lifecycleOwner) {
            // Create an observer that triggers our remembered callbacks
            // for sending analytics events
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_CREATE -> {

                    }
                    Lifecycle.Event.ON_START -> {
                    }
                    Lifecycle.Event.ON_RESUME -> {
//                              viewModel.getCallLogs()
                    }
                    Lifecycle.Event.ON_PAUSE -> {
                    }
                    Lifecycle.Event.ON_STOP -> {
                    }
                    Lifecycle.Event.ON_DESTROY -> {
                    }
                    else -> {
                    }
                }
            }

            // Add the observer to the lifecycle
            lifecycleOwner.lifecycle.addObserver(observer)

            // When the effect leaves the Composition, remove the observer
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }


    }
}
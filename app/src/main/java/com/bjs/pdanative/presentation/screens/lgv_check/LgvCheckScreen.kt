package com.bjs.pdanative.presentation.screens.lgv_check

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bjs.pdanative.R
import com.ramcosta.composedestinations.annotation.Destination

//
// Created by gaurav on 05/05/22.
// Copyright (c) 2022 gc. All rights reserved.
//


@Composable
@Destination(start = true)
fun ExteriorCheckScreen(
//    navigator: DestinationsNavigator ,
    viewModel: LgvCheckViewModel = hiltViewModel()
) {

    val state = viewModel.state




    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
                .background(color = colorResource(id = R.color.blackSecondary)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "LGV CHECK",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = colorResource(id = R.color.white),

                modifier = Modifier.align(alignment = Alignment.CenterVertically)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = colorResource(id = R.color.light_black)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            LazyRow(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(state.checks.size) { i ->
                    val lgvCheckInfo = state.checks[i]

                    HeaderIcon(icon = lgvCheckInfo.checkIcon, modifier = Modifier.clickable {
                        //  viewModel.onEvent(LgvCheckUIEvents.OnFaultSelected(lgvCheckInfo))
                    }, lgvCheckInfo = lgvCheckInfo, position = i)

                    if (i != state.checks.lastIndex){
                        Spacer(modifier = Modifier.width(2.dp))
                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .width(20.dp)
                                .background(color = colorResource(id = R.color.white))
                        )
                        Spacer(modifier = Modifier.width(2.dp))

                    }




                }

            }

            /* HeaderIcon(icon = R.drawable.ic_truck_solid)
             HeaderIcon(icon = R.drawable.ic_fuel)
             HeaderIcon(icon = R.drawable.ic_tyre)
             HeaderIcon(icon = R.drawable.ic_vehicle_fault)
             HeaderIcon(false, icon = R.drawable.ic_interior)*/

        }


        ShowLgvCheckData(
            viewModel = viewModel,
            modifier = Modifier.weight(7f, fill = true),
            lgvCheckInfo = state.checks.get(state.selected)
        )


    }


}


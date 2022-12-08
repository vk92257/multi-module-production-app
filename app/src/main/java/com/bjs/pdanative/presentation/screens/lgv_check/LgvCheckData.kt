package com.bjs.pdanative.presentation.screens.lgv_check

import android.content.Context
import com.bjs.pdanative.R
import com.bjs.pdanative.domain.models.lgvCheck.LgvCheckInfo


//
// Created by Gaurav Chauhan on 12/05/22.
// Copyright (c) 2022 BJS. All rights reserved.
//


object LgvCheckData {



    fun getLgvCheckData(context: Context): MutableList<LgvCheckInfo> {
        return mutableListOf<LgvCheckInfo>(
            LgvCheckInfo(
                checkTitle = context.getString(R.string.exterior_check_title),
                checkDescription = context.getString(R.string.exterior_check_desc),
                checkTip = context.getString(R.string.exterior_check_tip),
                checkIcon = R.drawable.ic_truck_solid,
                faultTitle = context.getString(R.string.exterior_fault_title),
                lgvCheckId = 0
            ),
            LgvCheckInfo(
                checkTitle = context.getString(R.string.fuel_title),
                checkDescription = context.getString(R.string.fuel_check_desc),
                checkTip = context.getString(R.string.fuel_check_tip),
                checkIcon = R.drawable.ic_fuel,
                faultTitle = context.getString(R.string.fuel_fault_title),
                lgvCheckId = 1

            ),
            LgvCheckInfo(
                checkTitle = context.getString(R.string.tyre_pressure_title),
                checkDescription = context.getString(R.string.tyre_pressure_desc),
                checkTip = context.getString(R.string.tyre_pressure_tip),
                checkIcon = R.drawable.ic_tyre,
                faultTitle = context.getString(R.string.tyre_pressure_fault_title),
                lgvCheckId = 2
            ),
            LgvCheckInfo(
                checkTitle = context.getString(R.string.vehicle_fault),
                checkDescription = context.getString(R.string.vehicle_fault_desc),
                checkTip = context.getString(R.string.vehicle_fault_tip),
                checkIcon = R.drawable.ic_vehicle_fault,
                faultTitle = context.getString(R.string.vehicle_fault_title),
                lgvCheckId = 3
            ),
            LgvCheckInfo(
                checkTitle = context.getString(R.string.interior_title),
                checkDescription = context.getString(R.string.interior_check_desc),
                checkTip = context.getString(R.string.interior_check_tip),
                checkIcon = R.drawable.ic_interior,
                faultTitle = context.getString(R.string.interior_fault_title),
                lgvCheckId = 4
            )

        )

    }


}
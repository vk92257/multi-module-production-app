package com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.logDamageScreen


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.bjs.pdanative.domain.models.logDamage.LogDamageFaultModel


data class LogDamageState(

    /*Log Damage Form */
    var damagedComponentList: List<String> = listOf(
        "Damaged Component",
        "Wing Mirror",
        "Engine",
        "Other"
    ),
    var priorityList: List<String> = listOf("Priority", "Low", "Medium", "Urgent"),
    var name: String = "Exterior",
    var openCamera: Boolean = false,
    var openFaultFormScreen: Boolean = true,
    var logDamageFaultModel: LogDamageFaultModel = LogDamageFaultModel(),

    /* Log Damage List  */
    var faultList: SnapshotStateList<LogDamageFaultModel> = mutableStateListOf(),
    var updateItemPosition: Int = -1,
    var isItemDelete: Boolean = false,
    var exitConfirmation: Boolean = false,
    var isExitFromScreen: Boolean = false,
)

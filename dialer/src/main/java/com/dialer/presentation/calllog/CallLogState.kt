package com.dialer.presentation.calllog

import androidx.compose.runtime.mutableStateMapOf
import com.dialer.domain.model.CallLogsPojo

/**
 * @Author: Vivek
 * @Date: 01/12/22
 */
data class CallLogState(
    val callLogs: MutableMap<String, ArrayList<CallLogsPojo>> = mutableStateMapOf(),
    val isExpandedViewEnable: Boolean = false,
    val expandedViewIndex: Int = -1,

    )
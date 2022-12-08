package com.dialer.presentation.contacthistoryscreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.dialer.domain.model.CallLogsPojo

/**
 * @Author: Vivek
 * @Date: 01/12/22
 */
data class ContactHistoryState(
    val contacts: SnapshotStateList<CallLogsPojo> = mutableStateListOf(),
    val isExpandedViewEnable: Boolean = false,
    val expandedViewIndex: Int = -1,

    )
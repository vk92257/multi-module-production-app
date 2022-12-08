package com.core.data.local.callLogs.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author: Vivek
 * @Date: 29/11/22
 */


@Parcelize
class CallLogsModel(
    var nestedData: HashMap<String, ArrayList<CallDetailsEntity>>? = null,
) : Parcelable
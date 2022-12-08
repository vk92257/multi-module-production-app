package com.core.data.local.callLogs.relation

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.core.data.local.callLogs.entity.CallLogsEntity
import com.core.data.local.callLogs.entity.ContactHistoryEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CallLogEntityWithContactHistoryEntity(
    @Embedded val callLogEntity: CallLogsEntity, @Relation(
        parentColumn = "number",
        entityColumn = "number",
    ) val contactHistoryEntityTest: List<ContactHistoryEntity>
) : Parcelable

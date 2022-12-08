package com.core.domain.repository

import com.core.data.local.callLogs.entity.CallLogsEntity
import com.core.data.local.callLogs.entity.ContactHistoryEntity
import com.core.data.local.callLogs.relation.CallLogEntityWithContactHistoryEntity
import kotlinx.coroutines.flow.Flow

interface CallLogRepository {
    fun insertCallLogList(callLogList: List<CallLogsEntity>)
    fun insertCallLog(callLog: CallLogsEntity)
    fun insertContactHistoryEntityList(contactHistoryEntityList: List<ContactHistoryEntity>)
    fun insertContactHistory(contactHistoryEntityList: ContactHistoryEntity)
    fun getContactHistoryList(): Flow<List<ContactHistoryEntity>>
    fun getCallLog(phoneNumber: String, dayAndYear: String): CallLogsEntity?
   suspend fun getCallLogsList(): Flow<List<CallLogsEntity>>
    fun getContactHistory(data: Long): ContactHistoryEntity
    fun updateContactHistory(timeStamp: Long, status: String, duration: String)
    fun updateContactHistory(timeStamp: Long, duration: String)
    fun updateCallLog(
        phoneNumber: String,
        dayAndYear: String,
        type: String,
        callDayTime: String,
        data: Long,
        duration: String,
        count: Int
    )

    fun getCallLogCount(phoneNumber: String, dayAndYear: String): CallLogsEntity
    fun getContactsWithHistory(phoneNumber: String): Flow<List<CallLogEntityWithContactHistoryEntity>>
    fun deleteCallLogList()
    fun deleteContactHistory()
}
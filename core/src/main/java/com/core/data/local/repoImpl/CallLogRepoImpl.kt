package com.core.data.local.repoImpl

import com.core.data.local.callLogs.entity.CallLogsEntity
import com.core.data.local.callLogs.CallLogsDao
import com.core.data.local.callLogs.entity.ContactHistoryEntity
import com.core.data.local.callLogs.relation.CallLogEntityWithContactHistoryEntity
import com.core.domain.repository.CallLogRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CallLogRepoImpl @Inject constructor(private val callLogsDao: CallLogsDao) :
    CallLogRepository {
    override fun insertCallLogList(callLogList: List<CallLogsEntity>) {
        return callLogsDao.insertCallLogsList(callLogList)
    }

    override fun insertCallLog(callLog: CallLogsEntity) {
        callLogsDao.insertCallLogs(callLog)
    }

    override fun insertContactHistoryEntityList(contactHistoryEntityList: List<ContactHistoryEntity>) {
        return callLogsDao.insertContactHistoryEntityList(contactHistoryEntityList)
    }

    override fun insertContactHistory(contactHistoryEntityList: ContactHistoryEntity) {
        callLogsDao.insertSingleContactHistoryEntity(contactHistoryEntityList)
    }

    override fun getContactHistoryList(): Flow<List<ContactHistoryEntity>> {
       return callLogsDao.getContactHistoryList()
    }

    override fun getCallLog(phoneNumber: String, dayAndYear: String): CallLogsEntity {
        return callLogsDao.getSingleCallLog(phoneNumber, dayAndYear)
    }

    override suspend fun getCallLogsList(): Flow<List<CallLogsEntity>> = callLogsDao.getCallLogList()


    override fun getContactHistory(data: Long): ContactHistoryEntity {
        return callLogsDao.getSingleContactHistory(data)
    }

    override fun updateContactHistory(timeStamp: Long, status: String, duration: String) {
        return callLogsDao.update(timeStamp, status, duration)
    }

    override fun updateContactHistory(timeStamp: Long, duration: String) {
        return callLogsDao.updateWithoutStatus(timeStamp, duration)
    }

    override fun updateCallLog(
        phoneNumber: String,
        dayAndYear: String,
        type: String,
        callDayTime: String,
        data: Long,
        duration: String,
        count: Int
    ) {
        return callLogsDao.updateSingleCallLog(
            phoneNumber,
            dayAndYear,
            type,
            callDayTime,
            data,
            duration,
            count,
        )
    }

    override fun getCallLogCount(phoneNumber: String, dayAndYear: String): CallLogsEntity {
        return callLogsDao.getSingleCallLogCount(phoneNumber, dayAndYear)
    }

    override fun getContactsWithHistory(phoneNumber: String): Flow<List<CallLogEntityWithContactHistoryEntity>> {
        return callLogsDao.getContactWithHistory(phoneNumber)
    }

    override fun deleteCallLogList() {
        return callLogsDao.deleteCallLogsList()
    }

    override fun deleteContactHistory() {
        return callLogsDao.deleteContactHistoryList()
    }

}
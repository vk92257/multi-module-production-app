package com.core.data.local.callLogs

import androidx.room.*
import com.core.data.local.callLogs.entity.CallLogsEntity
import com.core.data.local.callLogs.entity.ContactHistoryEntity
import com.core.data.local.callLogs.relation.CallLogEntityWithContactHistoryEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CallLogsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    fun insertCallLogsList(list: List<CallLogsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    fun insertCallLogs(entityTest: CallLogsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContactHistoryEntityList(contactHistoryList: List<ContactHistoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleContactHistoryEntity(contactHistoryList: ContactHistoryEntity)

    @Query("SELECT * FROM calllogsentity WHERE number = :phone AND dayandyear = :dayAndYear ")
    @Transaction
    fun getSingleCallLog(phone: String, dayAndYear: String): CallLogsEntity

    @Query("SELECT * FROM calllogsentity")
    @Transaction
    fun getCallLogList(): Flow<List<CallLogsEntity>>

    @Query("SELECT * FROM contacthistoryentity")
    @Transaction
    fun getContactHistoryList(): Flow<List<ContactHistoryEntity>>

    @Query("SELECT * FROM contacthistoryentity WHERE date = :date")
    fun getSingleContactHistory(date: Long): ContactHistoryEntity

    @Query("UPDATE contacthistoryentity SET duration=:duration , type=:status WHERE date=:timestamp ")
    @Transaction
    fun update(timestamp: Long, status: String, duration: String)

    @Query("UPDATE contacthistoryentity SET duration=:duration  WHERE date=:timestamp ")
    @Transaction
    fun updateWithoutStatus(timestamp: Long, duration: String)

    @Query("UPDATE calllogsentity SET type = :type , callDayTime = :callDayTime , date  = :date , duration = :duration , count = :count WHERE number = :phone AND dayandyear = :dayAndYear ")
    @Transaction
    fun updateSingleCallLog(
        phone: String,
        dayAndYear: String,
        type: String?,
        callDayTime: String?,
        date: Long?,
        duration: String?,
        count: Int,
    )

    @Query("SELECT * FROM calllogsentity WHERE number = :phone AND dayandyear = :dayAndYear ")
    @Transaction
    fun getSingleCallLogCount(phone: String, dayAndYear: String): CallLogsEntity

    @Query("SELECT * FROM calllogsentity WHERE number = :phoneNumber")
    @Transaction
    fun getContactWithHistory(phoneNumber: String): Flow<List<CallLogEntityWithContactHistoryEntity>>

    @Query("DELETE  FROM calllogsentity ")
    fun deleteCallLogsList()

    @Query("DELETE  FROM contacthistoryentity ")
    fun deleteContactHistoryList()


}
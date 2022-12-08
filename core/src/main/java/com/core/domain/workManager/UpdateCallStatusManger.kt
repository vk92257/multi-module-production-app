package com.core.domain.workManager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.core.data.local.callLogs.entity.CallLogsEntity
import com.core.data.local.callLogs.entity.ContactHistoryEntity
import com.core.domain.repository.CallLogRepository
import com.core.domain.repository.ContactRepository
import com.core.util.CallType
import com.core.util.ContactsDBConstants
import com.core.util.ContactsDBConstants.DATE_PATTERN
import com.core.util.ContactsDBConstants.Home
import com.core.util.ContactsDBConstants.Mobile
import com.core.util.ContactsDBConstants.Work
import com.core.util.Util
import com.core.util.Util.convertDate
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import java.util.*

/**
 * @Author: Vivek
 * @Date: 06/01/22
 */

@HiltWorker
class UpdateCallStatusManger @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val callLogRepository: CallLogRepository,
    private val contactRepository: ContactRepository,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            val data = workerParameters.inputData
            try {
                withContext(Dispatchers.IO) {
                    val callConnectTime: Long = data.getLong(ContactsDBConstants.CALL_TIMESTAMP, 0)
                    val callStatus: String? =
                        data.getString(ContactsDBConstants.CALL_DISCONNECTED_REASON_STATE)
                    val callCreationTime: Long =
                        data.getLong(ContactsDBConstants.CALL_CREATION_TIME, 0)
                    val currentTime: Long = data.getLong("current_time", 0)
                    // update call status in db
                    when (callStatus) {
                        ContactsDBConstants.CALL_REASON_LINE_BUSY -> {
                            /*
                            *   rejected by the receiver
                            * update the status by rejecting
                            * */
                            callLogRepository.updateContactHistory(
                                callCreationTime, CallType.REJECTED.toString(), "0"
                            )
                            val updateCallHistoryEntityTest: ContactHistoryEntity =
                                callLogRepository.getContactHistory(callCreationTime)
                            updateCallHistory(updateCallHistoryEntityTest)

                        }
                        ContactsDBConstants.CALL_REASON_LOCAL, ContactsDBConstants.CALL_REASON_NORMAL -> {

                            if (callConnectTime > 0L) {
                                val duration: Long = currentTime - callConnectTime
//                            update only the duration
//                              calculate the duration by subtracting from the current time
                                val updateCallHistoryEntityTest: ContactHistoryEntity =
                                    callLogRepository.getContactHistory(callCreationTime)
                                callLogRepository.updateContactHistory(
                                    callCreationTime, duration.toString()
                                )
                                updateCallHistory(updateCallHistoryEntityTest)
                            } else {
                                /*
                                   * if the duration is 0 than update the call
                                   * */
                                val updateCallHistoryEntityTest: ContactHistoryEntity =
                                    callLogRepository.getContactHistory(callCreationTime)
                                callLogRepository.updateContactHistory(
                                    callCreationTime, "0"
                                )
                                updateCallHistory(updateCallHistoryEntityTest)
                            }
                        }
                        ContactsDBConstants.CALL_REASON_INCOMING_MISSED -> {
                            /*
                            *  update the status with the incoming missed call
                            * */
                            val updateCallHistoryEntityTest: ContactHistoryEntity =
                                callLogRepository.getContactHistory(callCreationTime)
                            callLogRepository.updateContactHistory(
                                callCreationTime, CallType.MISSED.toString(), "0"
                            )
                            updateCallHistoryEntityTest.type = CallType.MISSED.toString()
                            updateCallHistory(updateCallHistoryEntityTest)
                        }
                        else -> {
                            val updateCallHistoryEntityTest: ContactHistoryEntity =
                                callLogRepository.getContactHistory(callCreationTime)
                            callLogRepository.updateContactHistory(
                                callCreationTime, CallType.UNKNOWN.toString(), "0"
                            )
                            updateCallHistory(updateCallHistoryEntityTest)
                        }
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure()
            }
            Result.success()
        } catch (error: Exception) {
            Result.failure()
        }
    }

    /*
    * Insert or Update the Call history into the DB
    * */
    private suspend fun updateCallHistory(
        currentCallObject: ContactHistoryEntity?
    ) {
        try {
            if (currentCallObject == null) return
            val currentDate = Date().time.let { Util.convertDate(it, DATE_PATTERN) }

            val callLogEntity = callLogRepository.getCallLog(
                currentCallObject.number.toString().takeLast(10),
                currentDate,
            )
            /* Checking if the  History for the current call is exists or not */
            if (callLogEntity == null) {
                /*Creating a new Call log History in DB*/
                val createSingleCallLogEntity = CallLogsEntity()
                createSingleCallLogEntity.apply {
                    number = currentCallObject.number.toString()
                    type = currentCallObject.type
                    count = 1
                    date = currentCallObject.date
                    dayAndYear = date!!.toLong().let { convertDate(it, DATE_PATTERN) }
                    duration = currentCallObject.duration
                    callDayTime = currentCallObject.callDayTime
                }
                contactRepository.searchContact(currentCallObject.number.toString().takeLast(10))
                    .collect { contactEntity ->
                        if (contactEntity != null) {
                            createSingleCallLogEntity.apply {
                                carrierReference = contactEntity.carrierReference
                                postcode = contactEntity.postcode
                                dropNumber = contactEntity.dropNumber
                                recipient =
                                    if (contactEntity.name.isNullOrEmpty()) contactEntity.name else contactEntity.name
                                orderNumber = contactEntity.orderNumber
                                if (currentCallObject.number.toString()
                                        .takeLast(10) == contactEntity.number
                                ) {
                                    typeOfContact = Mobile
                                } else if (currentCallObject.number.toString()
                                        .takeLast(10) == contactEntity.contactHome.toString()
                                ) {
                                    typeOfContact = Home
                                } else if (currentCallObject.number.toString()
                                        .takeLast(10) == contactEntity.contactWork.toString()
                                ) {
                                    typeOfContact = Work
                                }
                            }
                        }
                        callLogRepository.insertCallLog(createSingleCallLogEntity)
                    }
            } else {
                callLogRepository.updateCallLog(
                    currentCallObject.number.toString().takeLast(10),
                    currentDate,
                    currentCallObject.type ?: "",
                    currentCallObject.callDayTime ?: "",
                    currentCallObject.date ?: 0,
                    currentCallObject.duration ?: "",
                    callLogEntity.count!! + 1
                )
            }
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }
}
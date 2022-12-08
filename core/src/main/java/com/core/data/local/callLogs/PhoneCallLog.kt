package com.core.data.local.callLogs

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.CallLog
import com.core.data.local.callLogs.entity.CallDetailsEntity
import com.core.data.local.callLogs.entity.CallLogsEntity
import com.core.data.local.callLogs.entity.CallLogsModel
import com.core.data.local.callLogs.entity.ContactHistoryEntity
import com.core.data.local.contacts.entity.ContactEntity
import com.core.domain.repository.CallLogRepository
import com.core.util.CallType
import com.core.util.ContactsDBConstants.DATE_PATTERN
import com.core.util.ContactsDBConstants.Home
import com.core.util.ContactsDBConstants.Mobile
import com.core.util.ContactsDBConstants.TIME_PATTERN
import com.core.util.ContactsDBConstants.Work
import com.core.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * @Author: Vivek
 * @Date: 29/11/22
 */


suspend fun getCallDetails(
    application: Context,
    value: List<ContactEntity>,
    callLogRepository: CallLogRepository,
): HashMap<String, CallLogsModel> = withContext(
    Dispatchers.IO
) {
    val contactDetailsList = HashMap<String, ArrayList<ContactHistoryEntity>>()
    val callLogNestedHasMpa = HashMap<String, CallLogsModel>()
    var count = 0
    val cr: ContentResolver = application.contentResolver
    val managedCursor: Cursor? = cr.query(CallLog.Calls.CONTENT_URI, null, null, null, null)
    val number = managedCursor?.getColumnIndex(CallLog.Calls.NUMBER)
    val type = managedCursor?.getColumnIndex(CallLog.Calls.TYPE)
    val date = managedCursor?.getColumnIndex(CallLog.Calls.DATE)
    val duration = managedCursor?.getColumnIndex(CallLog.Calls.DURATION)
    val name = managedCursor?.getColumnIndex(CallLog.Calls.CACHED_NAME)

    while (managedCursor?.moveToNext() == true) {
        count++
        val callLogsList = ArrayList<CallDetailsEntity>()
        var callLogsHashMap = HashMap<String, ArrayList<CallDetailsEntity>>()
        val contact = CallDetailsEntity()
        val contactDetailsEntityTest = ContactHistoryEntity()
        var isContactAlreadyExist = false
        val phNumber = number?.let { managedCursor.getString(it).takeLast(10) } // mobile number
        val callType = type?.let { managedCursor.getString(it) } // call type
        val callDate = date?.let { managedCursor.getString(it) } // call date
        val callName = name?.let { managedCursor.getString(it) } // call Name
        val callDayTime = Date(callDate!!.toLong())
        val callDuration = duration?.let { managedCursor.getString(it) }
        var dir: String? = null
        dir = when (callType?.toInt()) {
            CallLog.Calls.OUTGOING_TYPE -> CallType.OUTGOING.toString()
            CallLog.Calls.INCOMING_TYPE -> CallType.INCOMING.toString()
            CallLog.Calls.MISSED_TYPE -> CallType.MISSED.toString()
            else -> CallType.REJECTED.toString()
        }

        contact.name = callName ?: ""
        contact.number = phNumber ?: ""
        contact.type = dir
        contact.date = callDate
        contact.callDayTime = Util.getDateOrTime(callDayTime, TIME_PATTERN).toString()
        contact.duration = callDuration ?: ""


        contactDetailsEntityTest.name = callName
        contactDetailsEntityTest.number = phNumber?.toLong()
        contactDetailsEntityTest.type = dir
        contactDetailsEntityTest.date = callDate.toLong()
        contactDetailsEntityTest.callDayTime = Util.getDateOrTime(callDayTime, TIME_PATTERN)
        contactDetailsEntityTest.duration = callDuration

        /*   @Key is the date of the Contact
         */
        var key = callDate.toLong().let { Util.convertDate(it, DATE_PATTERN) }
        key = key.trim()

        filterCallLogsByDay(
            contactDetailsEntityTest, contactDetailsList
        )
        if (callLogNestedHasMpa.size > 0) {
            if (callLogNestedHasMpa.containsKey(key)) {
                callLogsHashMap = callLogNestedHasMpa[key]!!.nestedData!!
                if (callLogsHashMap.containsKey(contact.toString())) {
                    val tempList = callLogsHashMap[contact.toString()]
                    tempList!!.add(contact)
                    tempList.sortBy {
                        it.date?.toLong()
                    }
                    tempList.reverse()
                    isContactAlreadyExist = true
                }
            }
        }
        if (!isContactAlreadyExist) {
            callLogsList.add(contact)
            callLogsHashMap[contact.toString()] = callLogsList
            val callLogsNumberDate = CallLogsModel()
            callLogsNumberDate.nestedData = callLogsHashMap
            callLogNestedHasMpa[key] = callLogsNumberDate
        }
    }
    managedCursor?.close()
    saveCallLogsToDB(contactDetailsList, value, callLogRepository)
    return@withContext callLogNestedHasMpa
}


private fun saveCallLogsToDB(
    contactDetailsList: HashMap<String, ArrayList<ContactHistoryEntity>>,
    ordersContactList: List<ContactEntity>,
    callLogRepository: CallLogRepository,
) = CoroutineScope(Dispatchers.IO).launch {
    val contactHistoryList = ArrayList(contactDetailsList.values)
    val callLogList = ArrayList<CallLogsEntity>()
    for (item in contactHistoryList) {
        val entity = CallLogsEntity()
        if (!item.isNullOrEmpty()) {
            entity.apply {
                val temp = item[item.size - 1]
                number = temp.number.toString()
                type = temp.type
                count = item.size
                date = temp.date
                dayAndYear = date!!.toLong().let { Util.convertDate(it, DATE_PATTERN) }
                duration = temp.duration
                callDayTime = temp.callDayTime
            }
            if (ordersContactList.isNotEmpty()) {
                ordersContactList.map { contactEntity: ContactEntity ->
                    val numberToSearch = entity.number.toString().takeLast(10)
                    if (contactEntity.number.toString()
                            .takeLast(10) == numberToSearch || contactEntity.contactHome?.takeLast(
                            10
                        ).equals(numberToSearch) || contactEntity.contactWork?.takeLast(10)
                            .equals(numberToSearch)
                    ) {
                        entity.apply {
                            if (numberToSearch == contactEntity.number.toString()) {
                                typeOfContact = Mobile
                            } else if (numberToSearch == contactEntity.contactHome.toString()) {
                                typeOfContact = Home
                            } else if (numberToSearch == contactEntity.contactWork.toString()) {
                                typeOfContact = Work
                            }
                            carrierReference = contactEntity.carrierReference
                            postcode = contactEntity.postcode
                            dropNumber = contactEntity.dropNumber
                            recipient = contactEntity.name
                            orderNumber = contactEntity.orderNumber
                        }
                        return@map
                    }
                }
                callLogList.add(entity)
                callLogRepository.insertCallLog(entity)
                callLogRepository.insertContactHistoryEntityList(item)
            } else {
                callLogList.add(entity)
                callLogRepository.insertCallLog(entity)
                callLogRepository.insertContactHistoryEntityList(item)
            }
        } else {
            /* TODO if the item is empty  */
        }
    }
}

fun filterCallLogsByDay(
    contactDetailsEntityTest: ContactHistoryEntity,
    contactDetailsList: HashMap<String, ArrayList<ContactHistoryEntity>>
) {
    var currentDate =
        contactDetailsEntityTest.date!!.toLong().let { Util.convertDate(it, DATE_PATTERN) }
    currentDate = "${currentDate}+ ${contactDetailsEntityTest.number}"
    if (contactDetailsList.containsKey(currentDate)) {
        val list = contactDetailsList[currentDate]
        list!!.add(contactDetailsEntityTest)
    } else {
        val newList = ArrayList<ContactHistoryEntity>()
        newList.add(contactDetailsEntityTest)
        contactDetailsList[currentDate] = newList
    }
}


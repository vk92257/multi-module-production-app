package com.dialer.presentation.contacthistoryscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.data.local.callLogs.relation.CallLogEntityWithContactHistoryEntity
import com.core.domain.repository.CallLogRepository
import com.core.util.ContactsDBConstants
import com.core.util.Util
import com.dialer.domain.model.CallLogsPojo
import com.dialer.domain.model.toCallLogsPojo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ContactHistoryViewModel @Inject constructor(private val callLogsRepository: CallLogRepository) :
    ViewModel() {
    var callLogEntity: CallLogsPojo = CallLogsPojo()
    var state by mutableStateOf(ContactHistoryState())
        private set
    var contacts: SnapshotStateList<CallLogsPojo> = mutableStateListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getContactHistory(callLog: CallLogsPojo) {
        viewModelScope.launch {
            callLogsRepository.getContactsWithHistory(callLog.number)
                .collectLatest { contactHistory: List<CallLogEntityWithContactHistoryEntity> ->
                    if (contactHistory.isNotEmpty()) contacts.clear() else return@collectLatest
                    contactHistory[0].contactHistoryEntityTest.sortedBy {
                        it.callDayTime?.let { it1 ->
                            Util.getDateFromStringTime(
                                it1, ContactsDBConstants.TIME_PATTERN
                            )
                        }
                    }.forEach {
                        /* Adding only  those items which have the same day in the History List */
                        if (Util.convertDate(
                                it.date ?: 0, ContactsDBConstants.DATE_PATTERN
                            ) == callLog.date
                        ) contacts.add(it.toCallLogsPojo())
                    }
                    state = state.copy(
                        contacts = contacts
                    )
                }
        }
    }

    fun updateCallLogPojo(callLogPojo: CallLogsPojo) {
        callLogEntity = callLogPojo
        getContactHistory(callLogPojo)
    }

}
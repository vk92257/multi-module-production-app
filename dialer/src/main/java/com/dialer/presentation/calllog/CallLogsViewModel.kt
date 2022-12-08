package com.dialer.presentation.calllog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.data.local.callLogs.entity.CallLogsEntity
import com.core.domain.repository.CallLogRepository
import com.core.util.ContactsDBConstants
import com.core.util.Util
import com.core.util.Util.isTodayOrYesterday
import com.dialer.domain.model.CallLogsPojo
import com.dialer.domain.model.toCallLogsPojo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CallLogsViewModel @Inject constructor(private val callLogsRepository: CallLogRepository) :
    ViewModel() {

    var state by mutableStateOf(CallLogState())
        private set

    init {
        getCallLogs()

    }

    private var callLogMap: MutableMap<String, ArrayList<CallLogsPojo>> = mutableMapOf()
    var logsMap: MutableMap<String, ArrayList<CallLogsPojo>> = mutableMapOf()


    @RequiresApi(Build.VERSION_CODES.O)
    fun getCallLogs() {
        viewModelScope.launch(Dispatchers.IO) {
            callLogsRepository.getCallLogsList().collectLatest { callLogs: List<CallLogsEntity> ->
                if (callLogs.isNotEmpty()) callLogMap.clear()
                callLogs.forEach { callLogEntity ->
                    val entity = callLogEntity.toCallLogsPojo()
                    val key = entity.date
                    if (callLogMap.containsKey(key)) {
                        val list = logsMap[key]
                        list?.add(entity)
                        callLogMap[key]!!.add(entity)
                    } else {
                        val list = arrayListOf<CallLogsPojo>()
                        list.add(entity)
                        callLogMap[key] = list
                    }
                }


                val map: List<MutableMap.MutableEntry<String, ArrayList<CallLogsPojo>>> =
                    callLogMap.entries.sortedByDescending {
                        Util.getDateFromStringTime(
                            it.key, ContactsDBConstants.DATE_PATTERN
                        )
                    }
                callLogMap.clear()
                
                map.forEach { maps ->
                    maps.value.sortByDescending {
                        Util.getDateFromStringTime(
                            it.time, ContactsDBConstants.TIME_PATTERN
                        )
                    }
                    callLogMap[isTodayOrYesterday(maps.key, ContactsDBConstants.DATE_PATTERN)] =
                        maps.value
                }

                withContext(Dispatchers.Main) {
                    delay(1000)
                    state = state.copy(
                        callLogs = callLogMap.toMutableMap()
                    )
                }


            }
        }

    }


}
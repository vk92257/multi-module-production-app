package com.dialer.data.call_service

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.telecom.Call
import android.telecom.InCallService
import androidx.annotation.RequiresApi
import com.core.data.local.callLogs.entity.ContactHistoryEntity
import com.core.domain.repository.CallLogRepository
import com.core.domain.repository.ContactRepository
import com.core.util.CallType
import com.core.util.ContactsDBConstants.TIME_PATTERN
import com.core.util.Util.convertDate
import com.dialer.util.CustomNotification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.M)
class CallService : InCallService() {


    companion object {
        var instance: CallService? = null
    }

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    @Inject
    lateinit var contactRepo: ContactRepository

    @Inject
    lateinit var callLogRepo: CallLogRepository

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCallAdded(call: Call?) {
        super.onCallAdded(call)
        uiScope.launch {
            if (call != null) {
                try {
                    saveCallState(call, callLogRepo)
                    contactRepo.searchContact(call.details.handle.schemeSpecificPart.takeLast(10))
                        .collectLatest {
                            if (instance != null) {
                                val schema = Uri.Builder().scheme("default_call_handler").build()
                                val intent = Intent(Intent.ACTION_VIEW, schema)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                delay(200)
                                withContext(Dispatchers.Main) {
                                    CustomNotification.createNotification(this@CallService)
                                    CallServiceHelper.registerCallBackListener(call, it)
                                }
                            }
                        }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    /*This method is important for inserting the call history entry for the first time and maintain the call  history and call log status*/

    @RequiresApi(Build.VERSION_CODES.R)
    fun saveCallState(call: Call, callLogRepository: CallLogRepository) {
        try {
            val number: String = call.details?.handle?.schemeSpecificPart.toString().takeLast(10)

            val contactHistoryEntity = ContactHistoryEntity()
            with(contactHistoryEntity) {
                this.number = number.toLong()
                this.duration = "0"
                this.date = call.details.creationTimeMillis
                this.callDayTime = convertDate(this.date!!, TIME_PATTERN)
            }

            val state =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) call.details?.state else call.state
            if (state == Call.STATE_RINGING) {
                contactHistoryEntity.type = CallType.INCOMING.toString()

            } else {
                contactHistoryEntity.type = CallType.OUTGOING.toString()
            }
            uiScope.launch {
                callLogRepository.insertContactHistory(contactHistoryEntity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }


    override fun onCallRemoved(call: Call?) {
        super.onCallRemoved(call)
        CallServiceHelper.unRegisterCallBackListener()
        CustomNotification.dismissNotification()
    }


}
package com.dialer.data.call_service

import android.os.Build
import android.telecom.Call
import android.telecom.VideoProfile
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.core.data.local.contacts.entity.ContactEntity
import com.core.domain.workManager.UpdateCallStatusManger
import com.core.util.ContactsDBConstants.CALL_CREATION_TIME
import com.core.util.ContactsDBConstants.CALL_DISCONNECTED_REASON_STATE
import com.core.util.ContactsDBConstants.CALL_REASON_UNKNOWN
import com.core.util.ContactsDBConstants.CALL_TIMESTAMP
import com.core.util.ContactsDBConstants.CURRENT_TIME
import com.core.util.CustomTimer
import com.dialer.domain.repository.CallDetailsEvents
import com.dialer.domain.repository.DialerUserActionEvents
import java.util.*


@RequiresApi(Build.VERSION_CODES.M)
object CallServiceHelper {


    private var currentCall: Call? = null
    private var secondCall: Call? = null
    private var tempCall: Call? = null

    private var contactEntityMap: MutableMap<String, ContactEntity> = mutableMapOf()
    var timerMap: MutableMap<String, CustomTimer> = hashMapOf()

    private var callDetailsEvents: CallDetailsEvents? = null
    private var callDetailsNotificationEvents: CallDetailsEvents? = null


    private val callback: Call.Callback = object : Call.Callback() {


        override fun onDetailsChanged(call: Call?, details: Call.Details?) {
            super.onDetailsChanged(call, details)
            when (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) details?.state else call?.state) {
                Call.STATE_ACTIVE -> {
                    callDetailsEvents?.onCallAccepted()
                    callDetailsNotificationEvents?.onCallAccepted()
                    val number = call!!.details.handle.schemeSpecificPart.takeLast(10)
                    if (!timerMap.containsKey(number)) {
                        timerMap[number] = getTime(number)
                    }

                    /*    *//*swap the call if the second call is accepted by the user *//*
                    if (secondCall != null && number == secondCall!!.details.handle.schemeSpecificPart.takeLast(
                            10
                        )
                    ) {
                        tempCall = currentCall
                        currentCall = secondCall
                        secondCall = tempCall
                    }*/


                }
                Call.STATE_AUDIO_PROCESSING -> {
                }
                Call.STATE_CONNECTING -> {
                }
                Call.STATE_DIALING -> {
                    callDetailsEvents?.onCallAccepted()
                    callDetailsNotificationEvents?.onCallAccepted()
                }
                Call.STATE_DISCONNECTED -> {
                    Log.e(
                        "TAG",
                        "onDetailsChanged: STATE_DISCONNECTED   ${call?.details?.handle?.schemeSpecificPart}",
                    )
                }
                Call.STATE_DISCONNECTING -> {
//                    callDetailsEvents?.onCallEnded(false)

                }
                Call.STATE_HOLDING -> {
                }
                Call.STATE_NEW -> {
                }
                Call.STATE_PULLING_CALL -> {
                }
                Call.STATE_RINGING -> {

                }
                Call.STATE_SELECT_PHONE_ACCOUNT -> {
                }
                Call.STATE_SIMULATED_RINGING -> {
                }
            }

        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onCallDestroyed(call: Call?) {
            super.onCallDestroyed(call)
            val number = call?.details?.handle?.schemeSpecificPart?.takeLast(10) ?: ""
            maintainCallHistoryAndCallLog(call)
            endCall(number)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun maintainCallHistoryAndCallLog(call: Call?) {
        val constraints = androidx.work.Constraints.Builder().build()
        val data = Data.Builder()
        data.apply {
            if (call!!.details.disconnectCause.reason.isNullOrEmpty()) putString(
                CALL_DISCONNECTED_REASON_STATE, CALL_REASON_UNKNOWN
            )
            else putString(CALL_DISCONNECTED_REASON_STATE, call.details.disconnectCause.reason)
            putLong(CALL_TIMESTAMP, call.details.connectTimeMillis)
            putLong(CALL_CREATION_TIME, call.details.creationTimeMillis)
            putLong(CURRENT_TIME, Date().time)
        }


/*
            if (call!!.details.disconnectCause.reason == CALL_REASON_INCOMING_MISSED) {

                *//*TODO Missed Call Logic*//*
                *//*currentCall?.let {
                    nameHasMap[call.details.handle.schemeSpecificPart.toString()]?.let { it1 ->
                        OnGoingCallNotification.missCallNotification(
                            name = it1,
                            call = it,
                            ctx = AppController.getContext()
                        )
                    }
                }*//*
            }*/

        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(UpdateCallStatusManger::class.java)
            .setConstraints(constraints).setInputData(data.build()).build()

        WorkManager.getInstance(CallService.instance!!.applicationContext)
            .enqueue(oneTimeWorkRequest)
    }

    private fun endCall(number: String) {
        if (timerMap.containsKey(number)) {
            timerMap[number]!!.stopTimer()
            timerMap.remove(number)
            val firstNumber =
                if (currentCall != null) currentCall!!.details.handle.schemeSpecificPart.takeLast(
                    10
                ) else ""
            val secondNumber =
                if (secondCall != null) secondCall!!.details.handle.schemeSpecificPart.takeLast(
                    10
                ) else ""
            if (number == firstNumber) {
                currentCall = secondCall
                secondCall = null
                tempCall = null
            } else if (number == secondNumber) {
                secondCall = null
                tempCall = null
            }
        }

        /* no calls are running so we can empty the map */
        if (timerMap.isEmpty()) {
            currentCall = null
            secondCall = null
            tempCall = null
        }

        callDetailsEvents?.onCallEnded(true, number)
        callDetailsNotificationEvents?.onCallEnded(true, number)
    }


    fun registerCallBackListener(call: Call, contactEntity: ContactEntity?) {
        val number = call.details.handle.schemeSpecificPart.takeLast(10)
        val entity = contactEntity ?: ContactEntity(
            name = "Unknown",
            number = number,
            isOrder = false,
        )

        contactEntityMap[number] = entity
        /**  if the call comes for the first time than the current call will be null
         * Otherwise this is second call
         *  **/
        if (currentCall == null) {
            currentCall = call
            currentCall?.registerCallback(callback)
            callDetailsEvents?.onIncomingCall(entity, number)
            callDetailsNotificationEvents?.onIncomingCall(entity, number)
        } else {
            secondCall = call
            secondCall?.registerCallback(callback)
            callDetailsEvents?.secondIncomingCall(entity, number)
            callDetailsNotificationEvents?.secondIncomingCall(entity, number)
        }
    }


    fun unRegisterCallBackListener() {
        currentCall?.unregisterCallback(callback)
    }

    /** *  [getTime] is used for start a timer for the call as soon as call started    and its returns the timer object to stop the timer **/
    fun getTime(number: String): CustomTimer {
        val timer = CustomTimer()
        timer.startTimer(number)
        timer.getCurrentTime {
            callDetailsEvents?.onCallTimeChange(it, number)
            callDetailsNotificationEvents?.onCallTimeChange(it, number)
        }
        return timer
    }


    /*
    User  Related events that are triggered by the User to interact with the Call
    * ==========================================================================================================================================
    *
    * */

    /*
    * Handling the Events coming from the END user related to call
    * */
    private val dialerUserAction = object : DialerUserActionEvents {
        override fun acceptCall(number: String) {
            val currentCallNumber = currentCall!!.details.handle.schemeSpecificPart.takeLast(10)
            if (currentCallNumber == number) {
                currentCall!!.answer(VideoProfile.STATE_AUDIO_ONLY)
            } else if (secondCall != null) {
                if (secondCall!!.details.handle.schemeSpecificPart.takeLast(10) == number) {
                    tempCall = currentCall
                    currentCall = secondCall
                    secondCall = tempCall
                    tempCall = null
                    currentCall!!.answer(VideoProfile.STATE_AUDIO_ONLY)
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun rejectCall(number: String) {
            val secondNumber =
                if (secondCall != null) secondCall!!.details.handle.schemeSpecificPart.takeLast(
                    10
                ) else ""
            if (number == secondNumber) {
                tempCall = currentCall
                currentCall = secondCall
                secondCall = tempCall
                tempCall = null
            }
            currentCall?.disconnect()

            if (timerMap.size == 1) {
                maintainCallHistoryAndCallLog(currentCall)
                endCall(number)
            }

        }


        override fun holdCall(number: String) {
            val currentCallState =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) currentCall?.details?.state else currentCall?.state
            if (currentCallState == Call.STATE_HOLDING) {
                currentCall?.unhold()
                callDetailsEvents?.heldCallStatusChange(false, number)
                callDetailsNotificationEvents?.heldCallStatusChange(false, number)

            } else {
                currentCall?.hold()
                callDetailsEvents?.heldCallStatusChange(true, number)
                callDetailsNotificationEvents?.heldCallStatusChange(true, number)
            }

        }

        override fun swapCall(number: String) {
            val firstNumber = currentCall!!.details.handle.schemeSpecificPart.takeLast(10)
            val secondNumber = secondCall!!.details.handle.schemeSpecificPart.takeLast(10)
            if (number == firstNumber) {
                currentCall?.hold()
                tempCall = currentCall
                currentCall = secondCall
                secondCall = tempCall
                tempCall = null
                currentCall?.unhold()

            } else if (number == secondNumber) {
                secondCall?.hold()
                tempCall = currentCall
                currentCall = secondCall
                secondCall = tempCall
                tempCall = null
                currentCall?.unhold()
            }

            callDetailsEvents?.onSwapHoldCall(
                currentCall!!.details.handle.schemeSpecificPart.takeLast(
                    10
                )
            )
            callDetailsNotificationEvents?.onSwapHoldCall(
                currentCall!!.details.handle.schemeSpecificPart.takeLast(
                    10
                )
            )

        }

        override fun changeSpeakerPhoneState(state: Int) {
            CallService.instance?.setAudioRoute(state)
        }
    }


    /*
    * This method is important to send the Call events object to the user to interact
    * with the  Call
    * */
    fun provideDialerUserAction() = dialerUserAction


    fun registerCallDetailsEventsListener(callDetailEvents: CallDetailsEvents) {
        callDetailsEvents = callDetailEvents

    }


    fun registerCallDetailsEventListenerForNotification(callDetailEvents: CallDetailsEvents) {
        callDetailsNotificationEvents = callDetailEvents
    }


    fun unregisterCallDetailsEventsListenerForNotification() {
        callDetailsNotificationEvents = null
    }

    fun unregisterCallDetailsEventsListener() {
        callDetailsEvents = null
    }


}






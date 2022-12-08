package com.dialer.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.dialer.data.call_service.CallServiceHelper
import com.dialer.util.ACCEPT_CALL
import com.dialer.util.PHONE_NUMBER
import com.dialer.util.REJECT_CALL

@RequiresApi(Build.VERSION_CODES.M)
class CallActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val userActionEvents = CallServiceHelper.provideDialerUserAction()
        val number: String? = intent.getStringExtra(PHONE_NUMBER)
        when (intent.action) {
            ACCEPT_CALL -> {
                userActionEvents.acceptCall(number ?: "")
            }
            REJECT_CALL -> {
                userActionEvents.rejectCall(number ?: "")
            }
        }
    }
}
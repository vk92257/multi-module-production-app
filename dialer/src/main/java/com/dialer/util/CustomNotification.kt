package com.dialer.util

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.core.data.local.contacts.entity.ContactEntity
import com.dialer.CallActivity
import com.dialer.R
import com.dialer.data.call_service.CallServiceHelper
import com.dialer.domain.model.CallPojo
import com.dialer.domain.model.jsonToCallPojo
import com.dialer.domain.model.swapCall
import com.dialer.domain.model.toCallPojo
import com.dialer.domain.repository.CallDetailsEvents
import com.dialer.receiver.CallActionReceiver

/**
 * @Author: Vivek
 * @Date: 02/11/22
 */

@SuppressLint("StaticFieldLeak")
object CustomNotification {


    private var collapsedView: RemoteViews? = null
    private const val notificationId: Int = 1234

    // declaring variables
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private const val channelId = "pda_native Call"
    private const val description = "Test notification"
    private var callState: CallPojo? = null
    private var notificationCompatBuilder: NotificationCompat.Builder? = null
    private var notificationCompat: Notification? = null

    @SuppressLint("StaticFieldLeak")
    private var context: Context? = null


    // pendingIntent is an intent for future use i.e after
    // the notification is clicked, this intent will come into action
    @SuppressLint("StaticFieldLeak")
    @RequiresApi(Build.VERSION_CODES.M)
    fun createNotification(context: Context) {

        this.context = context
        CallServiceHelper.registerCallDetailsEventListenerForNotification(callDetailsLister)
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(context, CallActivity::class.java)
        intent.putExtra(
            CALL_SCREEN_DATA, callState

        )


        // FLAG_UPDATE_CURRENT specifies that if a previous
        // PendingIntent already exists, then the current one
        // will update it with the latest intent
        // 0 is the request code, using it later with the
        // same method again will get back the same pending
        // intent for future reference
        // intent passed here is to our afterNotification class
        val openAppPendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // RemoteViews are used to use the content of
        // some different layout apart from the current activity layout
        collapsedView = RemoteViews(context.packageName, R.layout.call_notification)
        // checking if android version is greater than oreo(API 26) or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

        }
        notificationCompatBuilder =
            NotificationCompat.Builder(context, channelId).setSmallIcon(R.drawable.bjssmall)
                .setContentIntent(openAppPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_CALL).setCustomContentView(collapsedView)
                .setOngoing(true).setSound(null).setAutoCancel(false).setChannelId(channelId)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())

        notificationCompat = notificationCompatBuilder!!.build()
        notificationManager.notify(notificationId, notificationCompat)


        updateNotification(callState)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun dismissNotification() {
        notificationManager.cancel(notificationId)
        CallServiceHelper.unregisterCallDetailsEventsListenerForNotification()
        callState = null
        notificationCompatBuilder = null
        notificationCompat = null
        context = null

    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun updateNotification(state: CallPojo?) {

        /** Pending intent for handling the click events for the accept or reject calls*/
        val acceptCallIntent = Intent(context, CallActionReceiver::class.java)
        acceptCallIntent.action = ACCEPT_CALL
        acceptCallIntent.putExtra(PHONE_NUMBER, state?.number)
        val acceptPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            acceptCallIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )


        val declineCallIntent = Intent(context, CallActionReceiver::class.java)
        declineCallIntent.action = REJECT_CALL
        declineCallIntent.putExtra(PHONE_NUMBER, state?.number)

        val declinePendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            declineCallIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )

        if (state?.isOrder == true) {
            collapsedView?.apply {
                setViewVisibility(R.id.notification_Bnumber, View.GONE)
                setViewVisibility(R.id.notification_recipient, View.VISIBLE)
//                setTextViewText(R.id.notification_Bnumber, state.carrierReference)
                setTextViewText(R.id.notification_recipient, state.name)
                setTextViewText(
                    R.id.notification_caller_name,
                    state.postcode.ifEmpty { state.name })
            }
        } else {
            collapsedView?.apply {
                setViewVisibility(R.id.notification_Bnumber, View.GONE)
                setTextViewText(R.id.notification_recipient, state?.name)
                setTextViewText(
                    R.id.notification_caller_name, state?.number
                )
            }
        }


        /***Setting the icon according  to the contact type */
        when (state?.contactType) {
            ContactType.HOME -> {
                collapsedView?.apply {
                    setViewVisibility(R.id.notification_home, View.VISIBLE)
                    setViewVisibility(R.id.notification_work, View.GONE)
                    setViewVisibility(R.id.notification_phone, View.GONE)
                }
            }

            ContactType.MOBILE -> {
                collapsedView?.apply {
                    setViewVisibility(R.id.notification_home, View.GONE)
                    setViewVisibility(R.id.notification_work, View.GONE)
                    setViewVisibility(R.id.notification_phone, View.VISIBLE)
                }
            }

            ContactType.OFFICE -> {
                collapsedView?.apply {
                    setViewVisibility(R.id.notification_home, View.GONE)
                    setViewVisibility(R.id.notification_work, View.VISIBLE)
                    setViewVisibility(R.id.notification_phone, View.GONE)
                }
            }
            else -> {
                collapsedView?.apply {
                    setViewVisibility(R.id.notification_home, View.GONE)
                    setViewVisibility(R.id.notification_work, View.GONE)
                    setViewVisibility(R.id.notification_phone, View.GONE)
                }
            }

        }

        if (state?.isIncomingCall == true) collapsedView?.setViewVisibility(
            R.id.notification_accept_call, View.VISIBLE
        ) else collapsedView?.setViewVisibility(
            R.id.notification_accept_call, View.GONE
        )


        if (state?.isIncomingCall == true) {
            collapsedView?.setViewVisibility(R.id.notification_decline_call, View.VISIBLE)
            collapsedView?.setViewVisibility(R.id.notification_accept_call, View.VISIBLE)
        } else {
            collapsedView?.setViewVisibility(R.id.notification_decline_call, View.VISIBLE)
            collapsedView?.setViewVisibility(R.id.notification_accept_call, View.GONE)
        }

        collapsedView?.setOnClickPendingIntent(R.id.notification_decline_call, declinePendingIntent)
        collapsedView?.setOnClickPendingIntent(R.id.notification_accept_call, acceptPendingIntent)


        /***   Setting the time for the notification */
        collapsedView?.setTextViewText(R.id.notification_time, callState?.time)


        /*** Setting the type of the call in the notification view */
        val contentTextId = when (state?.type) {
            CallType.CALLING -> {
                CallType.CALLING
            }
            CallType.INCOMING -> {
                CallType.INCOMING
            }
            else -> {
                CallType.ONGOING
            }
        }

        collapsedView?.setTextViewText(R.id.notification_call_status, contentTextId.toString())


        /***Opening app intent on notification tap*/

        val intent = Intent(context, CallActivity::class.java)
        intent.putExtra(
            CALL_SCREEN_DATA, callState
        )
        val openAppPendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        notificationCompatBuilder!!.setContentIntent(openAppPendingIntent)
        notificationCompatBuilder!!.setContentTitle(state?.name)
        notificationManager.notify(notificationId, notificationCompat)

    }


    private val callDetailsLister = object : CallDetailsEvents {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onIncomingCall(contactEntity: ContactEntity, number: String) {
            callState = CallPojo()
            callState = contactEntity.toCallPojo(callPojo = callState!!, userNumber = number)
            callState!!.apply {
                type = CallType.INCOMING
                isIncomingCall = true
            }
            updateNotification(callState)
        }

        override fun onOutgoingCall() {

        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onCallAccepted() {
            callState!!.apply {
                type = CallType.ONGOING
                isIncomingCall = false
                showHoldView = (callState!!.secondCall != null)
            }
            updateNotification(callState)
        }

        override fun onCallRejected() {

        }

        override fun onCallHold() {
        }

        override fun currentCallInfo() {
        }

        override fun onSwapHoldCall(activeCallNumber: String) {
            callState = callState!!.swapCall(
                currentCall = callState!!,
                newCall = callState?.jsonToCallPojo(callState?.secondCall!!)!!
            )
        }

        override fun onCallEnded(value: Boolean, number: String) {
        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onCallTimeChange(time: String, number: String) {
            if (callState?.number == number) callState!!.apply {
                this.time = time
            } else {
                callState?.time = ""
            }
            updateNotification(callState)

        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun secondIncomingCall(entity: ContactEntity, number: String) {
            val newCall = entity.toCallPojo(callPojo = callState!!, userNumber = number)
            callState = callState!!.swapCall(callState!!, newCall)
            callState!!.apply {
                type = CallType.INCOMING
                isIncomingCall = true
            }
            updateNotification(callState)
        }

        override fun heldCallStatusChange(isHold: Boolean, number: String) {
            callState?.apply {
                isHoldPressed = isHold
            }
        }
    }

}
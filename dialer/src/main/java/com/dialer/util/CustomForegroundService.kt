package com.dialer.util

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.core.data.local.contacts.entity.ContactEntity
import com.dialer.CallActivity
import com.dialer.R
import com.dialer.domain.model.CallPojo
import com.dialer.domain.model.toCallPojo
import com.dialer.domain.repository.CallDetailsEvents

/**
 * @Author: Vivek
 * @Date: 03/11/22
 */
class CustomForegroundService : Service() {
    // declaring variables
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var builder: Notification.Builder
    private val channelId = "pda_native Call"
    private val description = "Test notification"
    private var callState = CallPojo()
    private var notificationCompatBuilder: NotificationCompat.Builder? = null
    private var notificationCompat: Notification? = null
    private val notificationId: Int = 12334

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotification(this)

        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()


    }


    // pendingIntent is an intent for future use i.e after
    // the notification is clicked, this intent will come into action
    @RequiresApi(Build.VERSION_CODES.M)
    fun createNotification(context: Context) {
//        CallServiceHelper.registerCallDetailsEventListenerForNotification(callDetailsLister)
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
        val collapsedView = RemoteViews(context.packageName, R.layout.call_notification)

        // checking if android version is greater than oreo(API 26) or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                channelId, description, NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

        }

        notificationCompatBuilder =
            NotificationCompat.Builder(context, channelId).setSmallIcon(R.drawable.bjssmall)
                .setContentIntent(openAppPendingIntent)
                .setContentTitle("PDA native custom notification ")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_CALL).setCustomContentView(collapsedView)
                .setOngoing(true).setSound(null).setAutoCancel(false).setChannelId(
                    channelId
                ).setStyle(NotificationCompat.DecoratedCustomViewStyle())

        notificationCompat = notificationCompatBuilder!!.build()
        startForeground(notificationId, notificationCompat)
//        notificationManager.notify(notificationId, notificationCompat)

    }


    fun updateNotification(name: String) {
        notificationCompatBuilder!!.setContentTitle(name)
    }

    private val callDetailsLister = object : CallDetailsEvents {
        override fun onIncomingCall(contactEntity: ContactEntity, number: String) {
            callState = contactEntity.toCallPojo(callPojo = callState, userNumber = number)
            callState = callState.copy(
                type = CallType.INCOMING,
                isIncomingCall = true,
            )

            updateNotification(callState.name)
        }

        override fun onOutgoingCall() {

        }

        override fun onCallAccepted() {

        }

        override fun onCallRejected() {

        }

        override fun onCallHold() {
        }

        override fun currentCallInfo() {
        }

        override fun onSwapHoldCall(activeCallNumber: String) {
        }

        override fun onCallEnded(value: Boolean, number: String) {
        }

        override fun onCallTimeChange(time: String, number: String) {
        }

        override fun secondIncomingCall(entity: ContactEntity, number: String) {
            callState = entity.toCallPojo(callPojo = callState, userNumber = number)
            callState = callState.copy(
                type = CallType.INCOMING,
                isIncomingCall = true,
            )


        }

        override fun heldCallStatusChange(isHold: Boolean, number: String) {
//            TODO("Not yet implemented")
        }
    }


    override fun onBind(p0: Intent?): IBinder? {
        return Binder()
    }
}
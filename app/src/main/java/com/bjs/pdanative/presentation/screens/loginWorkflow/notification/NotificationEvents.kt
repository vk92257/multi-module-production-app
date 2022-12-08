package com.bjs.pdanative.presentation.screens.loginWorkflow.notification

import com.bjs.pdanative.domain.models.notification.NotificationData

/**
 * @Author: Vivek
 * @Date: 10/03/22
 */
sealed class NotificationEvents {
    data class onNotificationItemClick(val notificationItem: NotificationData) :
        NotificationEvents()
    object onCallToActionClick : NotificationEvents()
    object onMarkAsReadClick : NotificationEvents()
    object onCloseClick : NotificationEvents()
}

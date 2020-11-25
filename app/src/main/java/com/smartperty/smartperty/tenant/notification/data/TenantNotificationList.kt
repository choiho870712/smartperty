package com.smartperty.smartperty.tenant.notification.data

data class TenantNotificationList(
    var list: MutableList<TenantNotification> = mutableListOf()
) {
}
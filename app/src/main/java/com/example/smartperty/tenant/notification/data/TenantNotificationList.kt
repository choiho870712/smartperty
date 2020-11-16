package com.example.smartperty.tenant.notification.data

data class TenantNotificationList(
    var list: MutableList<TenantNotification> = mutableListOf()
) {
}
package com.example.smartperty.tenant.home.equipmentManual.data

data class TenantEquipmentRoom(
    var room: String,
    var equipmentList: MutableList<TenantEquipmentItem>
) {
}
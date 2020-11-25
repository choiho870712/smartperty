package com.smartperty.smartperty.tenant.home.equipmentManual.data.model

import com.smartperty.smartperty.tenant.home.equipmentManual.data.TenantEquipmentItem

data class EquipmentManual(
    var manual: MutableList<TenantEquipmentItem>
) {
}
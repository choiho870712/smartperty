package com.smartperty.smartperty.tenant.home.equipmentManual.data.api

import com.smartperty.smartperty.tenant.home.equipmentManual.data.model.EquipmentManual
import io.reactivex.Single

interface ApiService {
    fun getEquipmentManual(): Single<EquipmentManual>
}
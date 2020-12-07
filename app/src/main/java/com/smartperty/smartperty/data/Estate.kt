package com.smartperty.smartperty.data

import android.graphics.Bitmap
import com.smartperty.smartperty.utils.GlobalVariables

data class Estate (
    var title: String = "",
    var address: String = "",
    var floor: Int = 0,
    var squareFt: Int = 0,
    var parkingSpace: String = "",
    var content: String = "",
    var contract: Contract = Contract(),
    var imageList: MutableList<Bitmap> = mutableListOf(),
    var repairList: MutableList<RepairOrder> = mutableListOf(
        RepairOrder()
    )
) {
    init {
        repairList.forEach {
            it.estate = this
        }
    }

    fun isRented(): Boolean {
        return contract.isEstablished()
    }
}

data class EstateList(
    var title: String = "title",
    var address: String = "address",
    var image: Bitmap? = null,
    var imageUrl: String = "",
    var list: MutableList<Estate> = mutableListOf(
        Estate(
            title = "幸福大樓3樓15室",
            address = "台北市大安街3號",
            squareFt = 30,
            floor = 6,
            parkingSpace = "樓下B1平面式",
            content = "冬暖夏涼\n四季沒蚊子\n每個都說好。週邊景點多，步行三分鐘到捷運站，夜市商圈通通都有",
            contract = Contract(
                rentAmount = 25000,
                rentPerMonthNumber = 1,
                rentEndDate = "2020年12月11日",
                tenant = UserInfo(
                    name = "Jason",
                    cellPhone = "0123456789"
                )
            )
        ),
        Estate(
            title = "大仁大樓3樓17室",
            address = "台北市大安街4號",
            squareFt = 30,
            floor = 6,
            parkingSpace = "樓下B1平面式",
            content = "冬暖夏涼\n四季沒蚊子\n每個都說好。週邊景點多，步行三分鐘到捷運站，夜市商圈通通都有",
            contract = Contract(
                rentAmount = 25000,
                rentPerMonthNumber = 1,
                rentEndDate = "2020年12月11日",
                tenant = UserInfo(
                    name = "Aiden",
                    cellPhone = "0123456789"
                )
            )
        ),
        Estate(
            title = "幸福大樓3樓15室",
            address = "台北市大安街3號",
            squareFt = 30,
            floor = 6,
            parkingSpace = "樓下B1平面式",
            content = "冬暖夏涼\n四季沒蚊子\n每個都說好。週邊景點多，步行三分鐘到捷運站，夜市商圈通通都有",
            contract = Contract(
                rentAmount = 25000,
                rentPerMonthNumber = 1,
                rentEndDate = "2020年12月11日",
                tenant = UserInfo(
                    name = "Hugo",
                    cellPhone = "0123456789"
                )
            )
        )
    )
) {
    init {
        list.forEach {
            it.contract.estate = it
            it.contract.landlord = GlobalVariables.landlord
        }
    }


    fun calculateSquareFt(): Int {
        var totalSquareFt = 0
        list.forEach {
            totalSquareFt += it.squareFt
        }
        return totalSquareFt
    }

    fun calculateRentRate(): Int {
        var rentCount = 0
        list.forEach {
            if (it.isRented())
                rentCount += 1
        }

        return rentCount*100/list.size
    }

}
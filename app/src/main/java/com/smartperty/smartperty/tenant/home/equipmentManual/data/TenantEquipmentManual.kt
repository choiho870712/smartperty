package com.smartperty.smartperty.tenant.home.equipmentManual.data

class TenantEquipmentManual{

    var manual = mutableListOf<TenantEquipmentRoom>()

    fun getItemList(): MutableList<TenantEquipmentItem> {
        val itemList = mutableListOf<TenantEquipmentItem>()
        manual.forEach{
            itemList.addAll(itemList.size, it.equipmentList)
        }
        return itemList
    }

    fun loadData() {
        manual = mutableListOf(
            TenantEquipmentRoom("客廳",
                mutableListOf(
                    TenantEquipmentItem("沙發", 1,
                        "https://www.hdlife.com.tw/zSharedFiles/GoodsManagement/021/086/021086.gallery.sharon_lan.1538466708-4.jpg"),
                    TenantEquipmentItem("椅子", 4,
                        "https://s.yimg.com/zp/MerchandiseImages/EE24046AAF-SP-5992917.jpg")
                )
            ),
            TenantEquipmentRoom("廚房",
                mutableListOf(
                    TenantEquipmentItem("瓦斯", 1,
                        "https://www.ikea.com.tw/dairyfarm/tw/pageImages/page__zh_tw_15892532090.jpeg")
                )
            ),
            TenantEquipmentRoom("睡房",
                mutableListOf(
                    TenantEquipmentItem("床", 1,
                        "https://www.ikea.com.tw/dairyfarm/tw/pageImages/page__zh_tw_16048949631285814812.jpeg")
                )
            )
        )
    }
}
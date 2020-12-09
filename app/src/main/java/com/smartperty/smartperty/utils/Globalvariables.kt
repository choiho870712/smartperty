package com.smartperty.smartperty.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.data.*
import com.smartperty.smartperty.landlord.menu.`object`.estateDirectory.EstateDirectoryAdapter
import com.smartperty.smartperty.landlord.menu.`object`.estateList.EstateListAdapter
import com.smartperty.smartperty.landlord.menu.personnel.data.LandlordPersonneInfo
import com.smartperty.smartperty.repair.EstateAdapter
import com.smartperty.smartperty.repair.RepairListAdapter
import org.threeten.bp.LocalDateTime
import java.text.SimpleDateFormat

class GlobalVariables : Application() {
    companion object {

        lateinit var activity: Activity
        lateinit var dbHelper: DBHelper
        val imageHelper = ImageHelper()
        val api = Api()

        lateinit var toolbar: Toolbar
        lateinit var toolBarUtils: ToolBarUtils
        var currentNavController: LiveData<NavController>? = null

        // account
        var user = User()
        var landlord = UserInfo()
        var tenant = UserInfo()
        var plumber = UserInfo()
        var personnel = UserInfo()
        var managerList = mutableListOf<UserInfo>(
            UserInfo(
                name = "manager Jason",
                company = "avatar",
                cellPhone = "023456789"
            ),
            UserInfo(
                name = "manager Aiden",
                company = "avatar",
                cellPhone = "023456781"
            ),
            UserInfo(
                name = "manager Hugo",
                company = "gama bear",
                cellPhone = "023456123"
            )
        )
        var accountantList = mutableListOf<UserInfo>(
            UserInfo(
                name = "accountant Jason",
                company = "avatar",
                cellPhone = "023456789"
            ),
            UserInfo(
                name = "accountant Aiden",
                company = "avatar",
                cellPhone = "023456781"
            ),
            UserInfo(
                name = "accountant Hugo",
                company = "gama bear",
                cellPhone = "023456123"
            )
        )
        var plumberList = mutableListOf<UserInfo>(
            UserInfo(
                name = "plumber Jason",
                company = "avatar",
                cellPhone = "023456789"
            ),
            UserInfo(
                name = "plumber Aiden",
                company = "avatar",
                cellPhone = "023456781"
            ),
            UserInfo(
                name = "plumber Hugo",
                company = "gama bear",
                cellPhone = "023456123"
            )
        )

        // repair
        var repairList = mutableListOf<RepairOrder>()
        lateinit var repairListLayoutManager: LinearLayoutManager
        var repairListAdapter:
                RecyclerView.Adapter<RepairListAdapter.CardHolder>? = null
        var repairOrder = RepairOrder()

        // estate
        var estate = Estate()
        lateinit var estateLayoutManager: LinearLayoutManager
        lateinit var estateAdapter: EstateAdapter
        var estateList = EstateList()
        lateinit var estateListLayoutManager: LinearLayoutManager
        var estateListAdapter:
                RecyclerView.Adapter<EstateListAdapter.CardHolder>? = null
        val estateDirectory = user.estateDirectory
        lateinit var estateDirectoryLayoutManager: LinearLayoutManager
        var estateDirectoryAdapter:
                RecyclerView.Adapter<EstateDirectoryAdapter.CardHolder>? = null

        // notification
        var notificationList = mutableListOf<Notification>(
            Notification(
                message = "合約類型",
                date = "2020/12/01",
                type = NotificationType.CONTRACT
            ),
            Notification(
                message = "租金類型",
                date = "2020/12/01",
                type = NotificationType.RENT
            ),
            Notification(
                message = "代辦類型",
                date = "2020/12/01",
                type = NotificationType.AGENT
            ),
            Notification(
                message = "維修類型",
                date = "2020/12/01",
                type = NotificationType.REPAIR
            ),
            Notification(
                message = "系統類型",
                date = "2020/12/01",
                type = NotificationType.SYSTEM
            )
        )

        @SuppressLint("SimpleDateFormat")
        fun getCurrentDateTime(): String {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm")
            return formatter.format(parser.parse(LocalDateTime.now().toString())!!)
        }
    }
}
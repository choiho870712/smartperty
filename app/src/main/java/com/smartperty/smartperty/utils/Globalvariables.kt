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
import com.smartperty.smartperty.landlord.menu.estate.EstateDirectoryAdapter
import com.smartperty.smartperty.landlord.menu.estate.EstateFolderAdapter
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

        // static storage
        var loginUser = User()
        var userList = mutableListOf<User>()
        var estateList = mutableListOf<Estate>()
        var repairList = mutableListOf<RepairOrder>()
        var contractList = mutableListOf<Contract>()

        // pointer
        var estateDirectory = loginUser.estateDirectory
        var estateFolder = EstateList()
        var estate = Estate()
        var repairOrder = RepairOrder()
        var contract = Contract()

        // adapter and manager
        var estateDirectoryAdapter:EstateDirectoryAdapter? = null
        var estateFolderAdapter:EstateFolderAdapter? = null
        var estateAdapter: EstateAdapter? = null
        var repairListAdapter:RepairListAdapter? = null
        lateinit var estateDirectoryLayoutManager: LinearLayoutManager
        lateinit var estateFolderLayoutManager: LinearLayoutManager
        lateinit var estateLayoutManager: LinearLayoutManager
        lateinit var repairListLayoutManager: LinearLayoutManager



        // account
        var landlord = User()
        var tenant = User()
        var plumber = User()
        var personnel = User()

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

        // chart
        var contractExpireLineChartDataSet = ChartData(
            type = ChartDataType.LINE_CHART,
            dataList = mutableListOf(
                ChartDataPair("11", 2),
                ChartDataPair("12", 3),
                ChartDataPair("13", 4),
                ChartDataPair("14", 5)
            )
        )
        var contractExpireIn3MonthBySquareFtPieChartDataSet = ChartData(
            type = ChartDataType.PIE_CHART,
            dataList = mutableListOf(
                ChartDataPair("0~20坪", 3),
                ChartDataPair("21~30坪", 5),
                ChartDataPair("31~50坪", 7),
                ChartDataPair("51~100坪", 9)
            )
        )
        var contractExpireIn3MonthByTypePieChartDataSet = ChartData(
            type = ChartDataType.PIE_CHART,
            dataList = mutableListOf(
                ChartDataPair("停車位", 2),
                ChartDataPair("辦公室", 4),
                ChartDataPair("店面", 6),
                ChartDataPair("套房", 8)
            )
        )
        var dataAnalysisByGroupBarChartDataSet = ChartData()
        var dataAnalysisByGroupPieChartDataSet = ChartData()
        var dataAnalysisByTypeBarChartDataSet = ChartData()
        var dataAnalysisByTypePieChartDataSet = ChartData()
        var dataAnalysisBySquareFtBarChartDataSet = ChartData()
        var dataAnalysisBySquareFtPieChartDataSet = ChartData()

        // finance
        var finance = Finance(
            income = mutableListOf(
                FinanceItem("租金",30000),
                FinanceItem("合計",30000)
            ),
            outcome = mutableListOf(
                FinanceItem("修繕 漏水 2020/03/20",8000),
                FinanceItem("修繕 冷氣 2020/01/20",10000),
                FinanceItem("地價",7500),
                FinanceItem("房屋稅",1500),
                FinanceItem("營業稅",1600),
                FinanceItem("廣告 519.c...",796),
                FinanceItem("水電",800),
                FinanceItem("利息 2%",24500),
                FinanceItem("合計",50000)
            )
        )

        @SuppressLint("SimpleDateFormat")
        fun getCurrentDateTime(): String {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm")
            return formatter.format(parser.parse(LocalDateTime.now().toString())!!)
        }

        fun logout() {

        }
    }
}
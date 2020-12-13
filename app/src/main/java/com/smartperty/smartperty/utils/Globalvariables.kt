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
        var landlord = User()
        var tenant = User()
        var plumber = User()
        var personnel = User()
        var managerList = mutableListOf<User>()
        var accountantList = mutableListOf<User>()
        var plumberList = mutableListOf<User>()
        var tenantList = mutableListOf<User>()

        // repair
        var repairList = mutableListOf<RepairOrder>()
        lateinit var repairListLayoutManager: LinearLayoutManager
        var repairListAdapter:
                RecyclerView.Adapter<RepairListAdapter.CardHolder>? = null
        var repairOrder = RepairOrder()

        // estate
        var estate = Estate()
        lateinit var estateLayoutManager: LinearLayoutManager
        var estateAdapter: EstateAdapter? = null
        var estateList = EstateList()
        lateinit var estateListLayoutManager: LinearLayoutManager
        var estateListAdapter:
                RecyclerView.Adapter<EstateListAdapter.CardHolder>? = null
        var estateDirectory = user.estateDirectory
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
            currentNavController = null

            // account
            user = User()
            landlord = User()
            tenant = User()
            plumber = User()
            personnel = User()
            managerList = mutableListOf<User>()
            accountantList = mutableListOf<User>()
            plumberList = mutableListOf<User>()
            tenantList = mutableListOf<User>()

            // repair
            repairList = mutableListOf<RepairOrder>()
            repairListAdapter = null
            repairOrder = RepairOrder()

            // estate
            estate = Estate()
            estateAdapter = null
            estateList = EstateList()
            estateListAdapter = null
            estateDirectory = user.estateDirectory
            estateDirectoryAdapter = null

            // notification
            notificationList = mutableListOf<Notification>(
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
            contractExpireLineChartDataSet = ChartData(
                type = ChartDataType.LINE_CHART,
                dataList = mutableListOf(
                    ChartDataPair("11", 2),
                    ChartDataPair("12", 3),
                    ChartDataPair("13", 4),
                    ChartDataPair("14", 5)
                )
            )
            contractExpireIn3MonthBySquareFtPieChartDataSet = ChartData(
                type = ChartDataType.PIE_CHART,
                dataList = mutableListOf(
                    ChartDataPair("0~20坪", 3),
                    ChartDataPair("21~30坪", 5),
                    ChartDataPair("31~50坪", 7),
                    ChartDataPair("51~100坪", 9)
                )
            )
            contractExpireIn3MonthByTypePieChartDataSet = ChartData(
                type = ChartDataType.PIE_CHART,
                dataList = mutableListOf(
                    ChartDataPair("停車位", 2),
                    ChartDataPair("辦公室", 4),
                    ChartDataPair("店面", 6),
                    ChartDataPair("套房", 8)
                )
            )
            dataAnalysisByGroupBarChartDataSet = ChartData()
            dataAnalysisByGroupPieChartDataSet = ChartData()
            dataAnalysisByTypeBarChartDataSet = ChartData()
            dataAnalysisByTypePieChartDataSet = ChartData()
            dataAnalysisBySquareFtBarChartDataSet = ChartData()
            dataAnalysisBySquareFtPieChartDataSet = ChartData()

            // finance
            finance = Finance(
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
        }
    }
}
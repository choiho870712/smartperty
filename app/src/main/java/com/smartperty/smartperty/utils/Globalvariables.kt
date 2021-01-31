package com.smartperty.smartperty.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.data.*
import com.smartperty.smartperty.landlord.menu.estate.EstateDirectoryAdapter
import com.smartperty.smartperty.landlord.menu.estate.EstateFolderAdapter
import com.smartperty.smartperty.repair.EstateAdapter
import com.smartperty.smartperty.repair.RepairListAdapter
import com.smartperty.smartperty.tenant.home.attractionsNearby.data.AttractionNearbyItem
import com.smartperty.smartperty.tools.TimeUtil
import org.threeten.bp.LocalDateTime
import java.text.SimpleDateFormat

class GlobalVariables : Application() {
    companion object {

        lateinit var activity: Activity
        lateinit var dbHelper: DBHelper
        val imageHelper = ImageHelper()
        val api = Api()

        var welcomeMessage = ""
        var weather = Weather()
        var rentedEstateList = EstateList(title = "已出租")
        var notRentedEstateList = EstateList(title = "未出租")

        lateinit var toolbar: Toolbar
        lateinit var toolBarUtils: ToolBarUtils
        var currentNavController: LiveData<NavController>? = null

        // static storage
        var loginUser = User()
        var userList = mutableListOf<User>()
        var estateList = mutableListOf<Estate>()
        var repairList = mutableListOf<RepairOrder>()
        var contractList = mutableListOf<Contract>()

        // address tree
        var addressTree = AddressTree()

        // pointer
        var estateDirectory = loginUser.estateDirectory
        var estateFolder = EstateList()
        var estate = Estate()
        var repairOrder = RepairOrder()
        var repairOrderPost = RepairOrderPost()
        var contract = Contract()
        var attractionList = mutableListOf<AttractionNearbyItem>()

        // adapter and manager
        var estateDirectoryAdapter:EstateDirectoryAdapter? = null
        var estateFolderAdapter:EstateFolderAdapter? = null
        var estateAdapter: EstateAdapter? = null
        var repairListAdapter:RepairListAdapter? = null
        lateinit var estateDirectoryLayoutManager: LinearLayoutManager
        lateinit var estateFolderLayoutManager: LinearLayoutManager
        lateinit var estateLayoutManager: LinearLayoutManager
        lateinit var repairListLayoutManager: LinearLayoutManager

        // switch
        var personnelUserInfoUsage: String = "read" // create / update / read
        var propertySelectorUsage: String = "rented" // rented / none
        var imageListUsage: String = "read" // read / edit

        var imageEditIndexLog: MutableList<Boolean> = mutableListOf()


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
                type = NotificationType.EVENT
            ),
            Notification(
                message = "系統類型",
                date = "2020/12/01",
                type = NotificationType.SYSTEM
            )
        )

        // chart
        var contractExpireLineChartDataSet = ChartData(
            type = ChartDataType.LINE_CHART)
        var contractExpireIn3MonthBySquareFtPieChartDataSet = ChartData(
            type = ChartDataType.PIE_CHART)
        var contractExpireIn3MonthByTypePieChartDataSet = ChartData(
            type = ChartDataType.PIE_CHART)
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

        fun getContractExpireIn3MonthList() : MutableList<Contract> {
            val list = mutableListOf<Contract>()

            contractList.forEach {
                if ((it.endDate - TimeUtil.getCurrentUnixTimeStamp()) < 3*30*24*60*60) {
                    list.add(it)
                }
            }

            list.sortBy { it.endDate }

            return list
        }

        fun getContractExpireIn3MonthByAreaList(rangeMin: Int, rangeMax: Int) : MutableList<Estate> {
            val contractExpireIn3MonthList = getContractExpireIn3MonthList()
            val list = mutableListOf<Estate>()

            contractExpireIn3MonthList.forEach {
                if (it.estate!!.area in (rangeMin + 1) until rangeMax) {
                    list.add(it.estate!!)
                }
            }

            list.sortBy { it.contract!!.endDate }

            return list
        }

        fun getContractExpireIn3MonthByTypeList(type:String) : MutableList<Estate> {
            val contractExpireIn3MonthList = getContractExpireIn3MonthList()
            val list = mutableListOf<Estate>()

            contractExpireIn3MonthList.forEach {
                if (it.estate!!.type == type) {
                    list.add(it.estate!!)
                }
            }

            list.sortBy { it.contract!!.endDate }

            return list
        }

        fun prepareContractChartDataSet() {
            var contract1MonthCount = 0
            var contract2MonthCount = 0
            var contract3MonthCount = 0

            contractList.forEach {
                if ((it.endDate - TimeUtil.getCurrentUnixTimeStamp())< 1*30*24*60*60) {
                    contract1MonthCount++
                }
                else if ((it.endDate - TimeUtil.getCurrentUnixTimeStamp())< 2*30*24*60*60) {
                    contract2MonthCount++
                }
                else if ((it.endDate - TimeUtil.getCurrentUnixTimeStamp())< 3*30*24*60*60) {
                    contract3MonthCount++
                }
            }

            val currentTimeStamp = TimeUtil.getCurrentDateTime()
            val currentMonth = currentTimeStamp.subSequence(5, 6).toString().toInt()


            contractExpireLineChartDataSet.dataList.clear()
            contractExpireLineChartDataSet.dataList.add(ChartDataPair(
                (currentMonth+1).toString(), contract1MonthCount))
            contractExpireLineChartDataSet.dataList.add(ChartDataPair(
                (currentMonth+2).toString(), contract2MonthCount))
            contractExpireLineChartDataSet.dataList.add(ChartDataPair(
                (currentMonth+3).toString(), contract3MonthCount))


            contractExpireIn3MonthBySquareFtPieChartDataSet.dataList.clear()
            var size = 0

            size = getContractExpireIn3MonthByAreaList(0,20).size
            if (size > 0) {
                contractExpireIn3MonthBySquareFtPieChartDataSet.dataList.add(
                    ChartDataPair(
                        tag = "0~20坪",
                        value = size,
                        rangeMin = 0,
                        rangeMax = 20
                    )
                )
            }

            size = getContractExpireIn3MonthByAreaList(20,30).size
            if (size > 0) {
                contractExpireIn3MonthBySquareFtPieChartDataSet.dataList.add(
                    ChartDataPair(
                        tag = "20~30坪",
                        value = size,
                        rangeMin = 20,
                        rangeMax = 30
                    )
                )
            }

            size = getContractExpireIn3MonthByAreaList(30,50).size
            if (size > 0) {
                contractExpireIn3MonthBySquareFtPieChartDataSet.dataList.add(
                    ChartDataPair(
                        tag = "30~50坪",
                        value = size,
                        rangeMin = 30,
                        rangeMax = 50
                    )
                )
            }

            size = getContractExpireIn3MonthByAreaList(50,100).size
            if (size > 0) {
                contractExpireIn3MonthBySquareFtPieChartDataSet.dataList.add(
                    ChartDataPair(
                        tag = "50~100坪",
                        value = size,
                        rangeMin = 50,
                        rangeMax = 100
                    )
                )
            }

            size = getContractExpireIn3MonthByAreaList(100,Int.MAX_VALUE).size
            if (size > 0) {
                contractExpireIn3MonthBySquareFtPieChartDataSet.dataList.add(
                    ChartDataPair(
                        tag = "100坪以上",
                        value = size,
                        rangeMin = 100,
                        rangeMax = Int.MAX_VALUE
                    )
                )
            }

            contractExpireIn3MonthByTypePieChartDataSet.dataList.clear()
            getContractExpireIn3MonthList().forEach {
                val tag = it.estate!!.type
                if (contractExpireIn3MonthByTypePieChartDataSet.dataList.find { it.tag == tag } == null) {
                    contractExpireIn3MonthByTypePieChartDataSet.dataList.add(
                        ChartDataPair(
                            tag = tag,
                            value = getContractExpireIn3MonthByTypeList(tag).size
                        )
                    )
                }
            }
        }

        fun getContractexpiringRentList() : MutableList<Contract> {
            val list = mutableListOf<Contract>()

            contractList.forEach {
                if ((it.getNextDate() - TimeUtil.getCurrentUnixTimeStamp()) < 3*30*24*60*60) {
                    list.add(it)
                }
            }

            list.sortBy { it.getNextDate() }

            return list
        }

        fun refreshAllChart() {
            Thread {
                GlobalVariables.dataAnalysisByGroupBarChartDataSet =
                    GlobalVariables.api.getBarChartByGroupTag(GlobalVariables.loginUser.id)
            }.start()
            Thread {
                GlobalVariables.dataAnalysisByGroupPieChartDataSet =
                    GlobalVariables.api.getPieChartByGroupTag(GlobalVariables.loginUser.id)
            }.start()
            Thread {
                GlobalVariables.dataAnalysisByTypeBarChartDataSet =
                    GlobalVariables.api.getBarChartByObjectType(GlobalVariables.loginUser.id)
            }.start()
            Thread {
                GlobalVariables.dataAnalysisByTypePieChartDataSet =
                    GlobalVariables.api.getPieChartByObjectType(GlobalVariables.loginUser.id)
            }.start()
            Thread {
                GlobalVariables.dataAnalysisBySquareFtBarChartDataSet =
                    GlobalVariables.api.getBarChartByArea(GlobalVariables.loginUser.id)
            }.start()
            Thread {
                GlobalVariables.dataAnalysisBySquareFtPieChartDataSet =
                    GlobalVariables.api.getPieChartByArea(GlobalVariables.loginUser.id)
            }.start()
            Thread {
                GlobalVariables.api.getContractByTimeIn3Months(GlobalVariables.loginUser.id)
            }.start()
        }

        fun logout() {
            welcomeMessage = ""
            weather = Weather()
            rentedEstateList = EstateList(title = "已出租")
            notRentedEstateList = EstateList(title = "未出租")

            currentNavController = null

            // static storage
            loginUser = User()
            userList = mutableListOf()
            estateList = mutableListOf()
            repairList = mutableListOf()
            contractList = mutableListOf()

            // address tree
            addressTree = AddressTree()

            // pointer
            estateDirectory = loginUser.estateDirectory
            estateFolder = EstateList()
            estate = Estate()
            repairOrder = RepairOrder()
            repairOrderPost = RepairOrderPost()
            contract = Contract()

            // adapter and manager
            estateDirectoryAdapter = null
            estateFolderAdapter = null
            estateAdapter = null
            repairListAdapter = null

            // switch
            personnelUserInfoUsage = "read" // create / update / read
            propertySelectorUsage = "rented" // rented / none


            // account
            landlord = User()
            tenant = User()
            plumber = User()
            personnel = User()

            // notification
            notificationList = mutableListOf(
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
                    type = NotificationType.EVENT
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
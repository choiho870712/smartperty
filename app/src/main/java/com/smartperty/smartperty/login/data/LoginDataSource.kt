package com.smartperty.smartperty.login.data

import com.smartperty.smartperty.data.UserType
import com.smartperty.smartperty.login.Result
import com.smartperty.smartperty.login.data.model.LoggedInUser
import com.smartperty.smartperty.utils.GlobalVariables
import java.io.IOException
import kotlin.concurrent.thread

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication

            return if (GlobalVariables.api.userLogin(username, password)) {
                GlobalVariables.user = GlobalVariables.api.getUserInformation(username)
                GlobalVariables.user.id = username
                GlobalVariables.user.account = username
                GlobalVariables.user.password = password
                val user = LoggedInUser(
                    java.util.UUID.randomUUID().toString(),
                    GlobalVariables.user.name
                )
                if (GlobalVariables.user.auth == UserType.LANDLORD) {
                    GlobalVariables.estateDirectory = GlobalVariables.user.estateDirectory
                    GlobalVariables.estateDirectory.forEach {
                        it.list = GlobalVariables.api.getPropertyByGroupTag(
                            it.title, GlobalVariables.user.id
                        )
                        it.list.forEachIndexed { index, estate ->
                            Thread {
                                for ( i in 0 until(estate.repairListId.size)) {
                                    val repairOrder =
                                        GlobalVariables.api.getEventInformation(estate.repairListId[i])
                                    repairOrder.estate = estate
                                    estate.repairList.add(repairOrder)
                                }
                            }.start()
                        }
                    }
                    Thread {
                        GlobalVariables.repairList =
                            GlobalVariables.api.landlordGetEventInformation(
                                GlobalVariables.user.id
                            )
                    }.start()
                    Thread {
                        GlobalVariables.user.tenantIdList.forEach {
                            GlobalVariables.tenantList.add(
                                GlobalVariables.api.getUserInformation(it)
                            )
                        }
                    }.start()
                    Thread {
                        GlobalVariables.user.agentIdList.forEach {
                            GlobalVariables.managerList.add(
                                GlobalVariables.api.getUserInformation(it)
                            )
                        }
                    }.start()
                    Thread {
                        GlobalVariables.user.accountantIdList.forEach {
                            GlobalVariables.accountantList.add(
                                GlobalVariables.api.getUserInformation(it)
                            )
                        }
                    }.start()
                    Thread {
                        GlobalVariables.user.technicianIdList.forEach {
                            GlobalVariables.plumberList.add(
                                GlobalVariables.api.getUserInformation(it)
                            )
                        }
                    }.start()
                    Thread {
                        GlobalVariables.dataAnalysisByGroupBarChartDataSet =
                            GlobalVariables.api.getBarChartByGroupTag(GlobalVariables.user.id)
                    }.start()
                    Thread {
                        GlobalVariables.dataAnalysisByGroupPieChartDataSet =
                            GlobalVariables.api.getPieChartByGroupTag(GlobalVariables.user.id)
                    }.start()
                    Thread {
                        GlobalVariables.dataAnalysisByTypeBarChartDataSet =
                            GlobalVariables.api.getBarChartByObjectType(GlobalVariables.user.id)
                    }.start()
                    Thread {
                        GlobalVariables.dataAnalysisByTypePieChartDataSet =
                            GlobalVariables.api.getPieChartByObjectType(GlobalVariables.user.id)
                    }.start()
                    Thread {
                        GlobalVariables.dataAnalysisBySquareFtBarChartDataSet =
                            GlobalVariables.api.getBarChartByArea(GlobalVariables.user.id)
                    }.start()
                    Thread {
                        GlobalVariables.dataAnalysisBySquareFtPieChartDataSet =
                            GlobalVariables.api.getPieChartByArea(GlobalVariables.user.id)
                    }.start()
                }
                Result.Success(user)
            } else {
                Result.Error(
                    IOException(
                        "Logging failed"
                    )
                )
            }
        } catch (e: Throwable) {
            return Result.Error(
                IOException(
                    "Error logging in",
                    e
                )
            )
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}


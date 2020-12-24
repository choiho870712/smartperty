package com.smartperty.smartperty.login.data

import com.smartperty.smartperty.login.Result
import com.smartperty.smartperty.login.data.model.LoggedInUser
import com.smartperty.smartperty.utils.GlobalVariables
import com.smartperty.smartperty.utils.Utils
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication

            return if (GlobalVariables.api.userLogin(username, password)) {
                GlobalVariables.loginUser = Utils.getUser(username)!!
                val user = LoggedInUser(
                    java.util.UUID.randomUUID().toString(),
                    GlobalVariables.loginUser.name
                )
                if (GlobalVariables.loginUser.auth == "landlord") {
                    Utils.getEstateDirectoryByGroupTag()
//                    Thread {
//                        GlobalVariables.repairList =
//                            GlobalVariables.api.landlordGetEventInformation(
//                                GlobalVariables.user.id
//                            )
//                    }.start()
//                    Thread {
//                        GlobalVariables.user.tenantIdList.forEach {
//                            GlobalVariables.tenantList.add(
//                                GlobalVariables.api.getUserInformation(it)
//                            )
//                        }
//                    }.start()
//                    Thread {
//                        GlobalVariables.user.agentIdList.forEach {
//                            GlobalVariables.managerList.add(
//                                GlobalVariables.api.getUserInformation(it)
//                            )
//                        }
//                    }.start()
//                    Thread {
//                        GlobalVariables.user.accountantIdList.forEach {
//                            GlobalVariables.accountantList.add(
//                                GlobalVariables.api.getUserInformation(it)
//                            )
//                        }
//                    }.start()
//                    Thread {
//                        GlobalVariables.user.technicianIdList.forEach {
//                            GlobalVariables.plumberList.add(
//                                GlobalVariables.api.getUserInformation(it)
//                            )
//                        }
//                    }.start()
//                    Thread {
//                        GlobalVariables.dataAnalysisByGroupBarChartDataSet =
//                            GlobalVariables.api.getBarChartByGroupTag(GlobalVariables.user.id)
//                    }.start()
//                    Thread {
//                        GlobalVariables.dataAnalysisByGroupPieChartDataSet =
//                            GlobalVariables.api.getPieChartByGroupTag(GlobalVariables.user.id)
//                    }.start()
//                    Thread {
//                        GlobalVariables.dataAnalysisByTypeBarChartDataSet =
//                            GlobalVariables.api.getBarChartByObjectType(GlobalVariables.user.id)
//                    }.start()
//                    Thread {
//                        GlobalVariables.dataAnalysisByTypePieChartDataSet =
//                            GlobalVariables.api.getPieChartByObjectType(GlobalVariables.user.id)
//                    }.start()
//                    Thread {
//                        GlobalVariables.dataAnalysisBySquareFtBarChartDataSet =
//                            GlobalVariables.api.getBarChartByArea(GlobalVariables.user.id)
//                    }.start()
//                    Thread {
//                        GlobalVariables.dataAnalysisBySquareFtPieChartDataSet =
//                            GlobalVariables.api.getPieChartByArea(GlobalVariables.user.id)
//                    }.start()
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


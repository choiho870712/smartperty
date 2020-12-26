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


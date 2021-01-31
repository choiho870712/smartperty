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
                val user = LoggedInUser(
                    java.util.UUID.randomUUID().toString(),
                    username
                )
                GlobalVariables.loginUser = Utils.getUser(username)!!
                GlobalVariables.weather =
                    GlobalVariables.api.getWeather("臺北市")
                GlobalVariables.welcomeMessage =
                    GlobalVariables.api.welcomeMessage(username)
                GlobalVariables.notificationList.addAll(
                    GlobalVariables.api.getMessage())
                if (GlobalVariables.loginUser.auth == "landlord") {
                    Thread {
                        Utils.getEstateDirectoryByGroupTag()
                    }.start()
                    GlobalVariables.refreshAllChart()
                    GlobalVariables.api.getPropertyRentalStatus(username)
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


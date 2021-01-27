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
                    Thread {
                        GlobalVariables.welcomeMessage =
                            GlobalVariables.api.welcomeMessage(username)
                    }.start()
                    Thread {
                        Utils.getEstateDirectoryByGroupTag()
                    }.start()
                    Thread {
                        GlobalVariables.notificationList.addAll(
                            GlobalVariables.api.getMessage()
                        )
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


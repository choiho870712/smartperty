package com.smartperty.smartperty.login.data

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
                GlobalVariables.user.id = username
                GlobalVariables.user.password = password
                GlobalVariables.api.getUserInformation(username)
                val user = LoggedInUser(java.util.UUID.randomUUID().toString(),
                    GlobalVariables.user.userInfo.name)
                Result.Success(user)
            }
            else {
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


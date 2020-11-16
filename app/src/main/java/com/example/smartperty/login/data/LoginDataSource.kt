package com.example.smartperty.login.data

import com.example.smartperty.login.Result
import com.example.smartperty.login.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication

            return if (username == "Landlord") {
                val user = LoggedInUser(java.util.UUID.randomUUID().toString(), "Landlord")
                Result.Success(user)
            } else {
                val user = LoggedInUser(java.util.UUID.randomUUID().toString(), "Tenant")
                Result.Success(user)
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


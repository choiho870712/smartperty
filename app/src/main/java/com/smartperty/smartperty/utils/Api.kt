package com.smartperty.smartperty.utils

import android.graphics.Bitmap
import com.smartperty.smartperty.data.EstateList
import com.smartperty.smartperty.data.UserType
import com.squareup.okhttp.*
import org.json.JSONObject
import java.io.IOException

class Api {
    private val client = OkHttpClient()
    private val jsonType: MediaType = MediaType.parse("application/json; charset=utf-8")

    private val urlDirectory = "https://64lz06kei0.execute-api.ap-southeast-1.amazonaws.com/Alpha/"

    private val urlGetUserInformation = urlDirectory + "accountmanagement/getuserinformation"
    private val urlLandlordOpenPermission = urlDirectory + "accountmanagement/landlordopenpermission"
    private val urlUpdateUserInformation = urlDirectory + "accountmanagement/updateuserinformation"
    private val urlUserLogin = urlDirectory + "accountmanagement/userlogin"
    private val urlCreatePropertyGroupTag = urlDirectory + "grouptagmanagement/createpropertygrouptag"
    private val urlGetGroupTag = urlDirectory + "grouptagmanagement/getgrouptag"
    private val urlUpdatePropertyGroupTagInformation = urlDirectory + "grouptagmanagement/updatepropertygrouptaginformation"
    private val urlGetObjectByGroupTag = urlDirectory + "propertymanagement/getobjectbygrouptag"

    fun getGroupTagJson(rawJsonString:String) {
        val jsonObject = JSONObject(rawJsonString)
        val items = jsonObject.getJSONObject("Items")
        val group = items.getJSONArray("group")

        for (i in 0 until(group.length())) {
            val groupItem = group.getJSONObject(i)
            val newGroup = EstateList(
                title = groupItem.getString("name"),
                imageUrl = groupItem.getString("image")
            )

            if (newGroup.imageUrl != "nil") {
                newGroup.image =
                    GlobalVariables.imageHelper.convertUrlToImage(newGroup.imageUrl)
            }

            GlobalVariables.user.estateDirectory.add(newGroup)

        }
    }

    fun getUserInformationJson(rawJsonString:String) {
        val jsonObject = JSONObject(rawJsonString)
        val items = jsonObject.getJSONObject("Items")

        // items
        GlobalVariables.user.userInfo.auth = UserType.getByString(items.getString("auth"))
        GlobalVariables.user.permissions = items.getString("permissions")
        GlobalVariables.user.system_id = items.getString("system_id")

        // information
        val information = items.getJSONObject("information")
        GlobalVariables.user.userInfo.name = information.getString("name")
        GlobalVariables.user.userInfo.email = information.getString("mail")
        GlobalVariables.user.userInfo.iconString = information.getString("icon")
        if (GlobalVariables.user.userInfo.iconString != "nil") {
            GlobalVariables.user.userInfo.icon = GlobalVariables.imageHelper.convertUrlToImage(
                GlobalVariables.user.userInfo.iconString
            )
        }

        // list
        val list = items.getJSONObject("lists")
        val accountant = list.getJSONArray("accountant")
        val agent = list.getJSONArray("agent")
        val contract = list.getJSONArray("contract")
        val event = list.getJSONArray("event")
        val group = list.getJSONArray("group")
        val technician = list.getJSONArray("technician")
        val tenant = list.getJSONArray("tenant")

        for (i in 0 until(accountant.length()))
            GlobalVariables.user.accountantIdList.add(accountant.getString(i))
        for (i in 0 until(agent.length()))
            GlobalVariables.user.agentIdList.add(agent.getString(i))
        for (i in 0 until(contract.length()))
            GlobalVariables.user.contractIdList.add(contract.getString(i))
        for (i in 0 until(event.length()))
            GlobalVariables.user.eventIdList.add(event.getString(i))
        for (i in 0 until(technician.length()))
            GlobalVariables.user.technicianIdList.add(technician.getString(i))
        for (i in 0 until(tenant.length()))
            GlobalVariables.user.tenantIdList.add(tenant.getString(i))

        for (i in 0 until(group.length())) {
            val groupItem = group.getJSONObject(i)
            val newGroup = EstateList(
                title = groupItem.getString("name"),
                imageUrl = groupItem.getString("image")
            )

            if (newGroup.imageUrl != "nil") {
                newGroup.image =
                    GlobalVariables.imageHelper.convertUrlToImage(newGroup.imageUrl)
            }

            GlobalVariables.user.estateDirectory.add(newGroup)

        }

    }

    fun getUserInformation(id:String) {
        val json = "{\"id\":\"$id\"}"
        getUserInformationJson(callApi(json, urlGetUserInformation))
    }

    fun getGroupTag(id:String) {
        val json = "{\"id\":\"$id\"}"
        return getGroupTagJson(callApi(json, urlGetGroupTag))
    }

    fun getPropertyByGroupTag(group_name:String, landlord_id:String): String {
        val json = "{\"group_name\":\"$group_name\", \"landlord_id\":\"$landlord_id\"}"
        return getJsonMessage(callApi(json, urlGetObjectByGroupTag))
    }

    fun updatePropertyGroupTagInformation(id:String, system_id:String, group_index:Int,
                                          name: String, image: Bitmap): String {
        val imageString = GlobalVariables.imageHelper.getString(image)
        val json = "{\"id\":\"$id\", \"system_id\":\"$system_id\", \"group_index\":$group_index," +
                " \"group\":{\"name\": \"$name\", \"image\": \"$imageString\"} }"
        return getJsonMessage(callApi(json, urlUpdatePropertyGroupTagInformation))
    }

    enum class LandlordOpenPermissionAuth {
        TECHNICIAN, ACCOUNTANT, AGENT, TENANT
    }

    enum class LandlordOpenPermissionPermissions {
        A, W, R, N, S
    }

    fun landlordOpenPermission(master_id:String, slave_id:String,
                               auth:LandlordOpenPermissionAuth,
                               permissions:LandlordOpenPermissionPermissions): String {
        val authString = auth.toString()
        val permissionString = permissions.toString()
        val json = "{\"master_id\": \"$master_id\", \"slave_id\": \"$slave_id\"," +
                " \"auth\": \"$authString\", \"permissions\": \"$permissionString\"}"
        return getJsonMessage(callApi(json, urlLandlordOpenPermission))
    }

    fun userLogin(id:String, pw:String) : Boolean{
        val json = "{\"id\": \"$id\", \"pw\": \"$pw\"}"
        return getJsonMessage(callApi(json, urlUserLogin)) == "No Error"
    }

    fun createPropertyGroupTag(id:String, system_id:String, name: String, image: Bitmap?) : Boolean {
        val imageString =
            if (image != null)
                GlobalVariables.imageHelper.getString(image)
            else
                "nil"
        val json = "{\"id\": \"$id\", \"system_id\": \"$system_id\"," +
                " \"group\":{\"name\": \"$name\", \"image\": \"$imageString\"}}"
        return getJsonMessage(callApi(json, urlCreatePropertyGroupTag)) == "No Error"
    }


    private fun getJsonMessage(rawJsonString:String) : String {
        val jsonObject = JSONObject(rawJsonString)
        return if ( jsonObject.has("Message") )
            jsonObject.getString("Message")
        else
            ""
    }

    private fun fixLineFeed(json: String): String {
        var newJson = ""
        for (char in json) {
            if (char == '\n') newJson += "\\n"
            else newJson += char
        }
        return newJson
    }

    private fun callApi(json:String, apiUrl: String) : String {
        val request = Request.Builder()
            .url(apiUrl)
            .post(RequestBody.create(jsonType, json))
            .build()

        var responseStrng = ""
        var needToCallAgain = false
        var isSuccess = false

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(request: Request, e: IOException) {
                responseStrng = e.message!!
                needToCallAgain = true
            }

            @Throws(IOException::class)
            override fun onResponse(response: Response) {
                responseStrng = response.body().string()
                isSuccess = true
            }
        })

        while (!isSuccess) {
            if (needToCallAgain) {
                needToCallAgain = false
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(request: Request, e: IOException) {
                        responseStrng = e.message!!
                        needToCallAgain = true
                    }

                    @Throws(IOException::class)
                    override fun onResponse(response: Response) {
                        responseStrng = response.body().string()
                        isSuccess = true
                    }
                })
            }
            Thread.sleep(500)
        }

        return responseStrng
    }
}

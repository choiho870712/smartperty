package com.smartperty.smartperty.utils

import android.graphics.Bitmap
import com.smartperty.smartperty.data.*
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
    private val urlGetObjectByGroupTag = urlDirectory + "propertymanagement/getpropertybygrouptag"
    private val urlLandlordGetEventInformation = urlDirectory + "eventmanagement/landlordgeteventinformation"
    private val urlGetPropertyByObjectId = urlDirectory + "propertymanagement/getpropertybyobjectid"
    private val urlCreateEvent = urlDirectory + "eventmanagement/createevent"

    private fun getPropertyByObjectIdJson(rawJsonString:String): Estate {
        val jsonObject = JSONObject(rawJsonString)
        val item = jsonObject.getJSONObject("Items")
        val eventHistoryIdList = item.getJSONArray("event_history")
        val contractHistoryIdList = item.getJSONArray("contract_history")
        val information = item.getJSONObject("information")
        val imageList = information.getJSONArray("image")
        val estate = Estate(
            objectId = item.getString("object_id"),
            contractId = item.getString("current_contract_id"),
            squareFt = information.getString("area").toInt(),
            address = information.getString("address"),
            road = information.getString("road"),
            street = information.getString("street"),
            title = information.getString("object_name"),
            district = information.getString("district"),
            contract = Contract(
                rentAmount = information.getInt("purchase_price"),
                tenant = User(
                    name = item.getString("tenant_id")
                ),
                landlord = User(
                    name = item.getString("landlord_id")
                )
            ),
            parkingSpace = information.getString("parking_space"),
            content = information.getString("description"),
            floor = information.getInt("floor"),
            type = information.getString("type")
        )

        for (j in 0 until(imageList.length()))
            estate.imageUrlList.add(imageList.getString(j))
        Thread {
            estate.imageUrlList.forEach {
                estate.imageList.add(GlobalVariables.imageHelper.convertUrlToImage(it)!!)
            }
        }.start()

        for (j in 0 until(contractHistoryIdList.length()))
            estate.contractHistoryIdList.add(contractHistoryIdList.getString(j))

        for (j in 0 until(eventHistoryIdList.length()))
            estate.repairListId.add(eventHistoryIdList.getString(j))

        return estate
    }

    private fun getObjectByGroupTagJson(rawJsonString:String): MutableList<Estate> {
        val estateList = mutableListOf<Estate>()

        val jsonObject = JSONObject(rawJsonString)
        val items = jsonObject.getJSONArray("Items")
        for (i in 0 until(items.length())) {
            val item = items.getJSONObject(i)
            val eventHistoryIdList = item.getJSONArray("event_history")
            val contractHistoryIdList = item.getJSONArray("contract_history")
            val information = item.getJSONObject("information")
            val imageList = information.getJSONArray("image")
            val estate = Estate(
                objectId = item.getString("object_id"),
                contractId = item.getString("current_contract_id"),
                squareFt = information.getString("area").toInt(),
                address = information.getString("address"),
                road = information.getString("road"),
                street = information.getString("street"),
                title = information.getString("object_name"),
                district = information.getString("district"),
                contract = Contract(
                    rentAmount = information.getInt("purchase_price"),
                    tenant = User(
                        name = item.getString("tenant_id")
                    ),
                    landlord = User(
                        name = item.getString("landlord_id")
                    )
                ),
                parkingSpace = information.getString("parking_space"),
                content = information.getString("description"),
                floor = information.getInt("floor"),
                type = information.getString("type")
            )

            for (j in 0 until(imageList.length()))
                estate.imageUrlList.add(imageList.getString(j))
            Thread {
                estate.imageUrlList.forEach {
                    estate.imageList.add(GlobalVariables.imageHelper.convertUrlToImage(it)!!)
                }
            }.start()

            for (j in 0 until(contractHistoryIdList.length()))
                estate.contractHistoryIdList.add(contractHistoryIdList.getString(j))

            for (j in 0 until(eventHistoryIdList.length()))
                estate.repairListId.add(eventHistoryIdList.getString(j))

            estateList.add(estate)
        }
        return estateList
    }

    private fun getGroupTagJson(rawJsonString:String) {
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

    private fun getUserInformationJson(rawJsonString:String): User {
        val user = User()
        val jsonObject = JSONObject(rawJsonString)
        if (!jsonObject.has("Items")) {
            return user
        }
        val items = jsonObject.getJSONObject("Items")

        // items
        user.auth = UserType.getByString(items.getString("auth"))
        user.permissions = items.getString("permissions")
        user.system_id = items.getString("system_id")

        // information
        val information = items.getJSONObject("information")
        user.name = information.getString("name")
        user.email = information.getString("mail")
        user.iconString = information.getString("icon")
        if (user.iconString != "nil") {
            user.icon = GlobalVariables.imageHelper.convertUrlToImage(
                user.iconString
            )
        }

        // list
        val list = items.getJSONObject("lists")

        if (list.has("accountant")){
            val accountant = list.getJSONArray("accountant")
            for (i in 0 until(accountant.length()))
                user.accountantIdList.add(accountant.getString(i))
        }
        if (list.has("agent")){
            val agent = list.getJSONArray("agent")
            for (i in 0 until(agent.length()))
                user.agentIdList.add(agent.getString(i))
        }
        if (list.has("contract")){
            val contract = list.getJSONArray("contract")
            for (i in 0 until(contract.length()))
                user.contractIdList.add(contract.getString(i))
        }
        if (list.has("event")){
            val event = list.getJSONArray("event")
            for (i in 0 until(event.length()))
                user.eventIdList.add(event.getString(i))
        }
        if (list.has("technician")){
            val technician = list.getJSONArray("technician")
            for (i in 0 until(technician.length()))
                user.technicianIdList.add(technician.getString(i))
        }
        if (list.has("tenant")){
            val tenant = list.getJSONArray("tenant")
            for (i in 0 until(tenant.length()))
                user.tenantIdList.add(tenant.getString(i))
        }
        if (list.has("group")){
            val group = list.getJSONArray("group")
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

                user.estateDirectory.add(newGroup)

            }
        }

        return user
    }

    private fun landlordGetEventInformationJson(rawJsonString:String): MutableList<RepairOrder> {
        val repairList = mutableListOf<RepairOrder>()

        val jsonObject = JSONObject(rawJsonString)
        val items = jsonObject.getJSONArray("Items")
        for (i in 0 until(items.length())) {
            val item = items.getJSONObject(i)
            val information = item.getJSONObject("information")
            val repairOrder = RepairOrder(
                timestamp = item.getInt("timestmp"),
                statusString = item.getString("status"),
                event_id = item.getString("event_id"),
                object_id = item.getString("object_id"),
                initiate_id = item.getString("initiate_id"),
                typeString = item.getString("type"),
                repairDateTime = information.getString("date"),
                title = information.getString("description")
            )

            val dynamic_status = information.getJSONArray("dynamic_status")
            for (j in 0 until(dynamic_status.length())) {
                val dynamic_status_item = dynamic_status.getJSONObject(j)
                val repairOrderPost = RepairOrderPost(
                    createDateTime = dynamic_status_item.getString("date"),
                    message = dynamic_status_item.getString("description")
                )

                val imageUrlListObject = dynamic_status_item.getJSONArray("image")
                for (k in 0 until(imageUrlListObject.length())) {
                    repairOrderPost.imageStringList.add(imageUrlListObject.getString(k))
                }
                Thread {
                    repairOrderPost.imageStringList.forEach {
                        repairOrderPost.imageList.add(
                            GlobalVariables.imageHelper.convertUrlToImage(it)!!)
                    }
                }.start()

                repairOrder.postList.add(repairOrderPost)
            }

            val participant = item.getJSONArray("participant")
            for (j in 0 until(participant.length())) {
                val participantItem = participant.getJSONObject(j)

                when (participantItem.getString("auth")) {
                    "technician" -> {
                        repairOrder.plumber_id = participantItem.getString("id")
                    }
                    "tenant" -> {
                        repairOrder.tenant_id = participantItem.getString("id")
                    }
                    else -> {
                    }
                }
            }

            if (repairOrder.object_id.length > 3) {
                Thread {
                    repairOrder.estate = getPropertyByObjectId(repairOrder.object_id)
                }.start()
            }
            repairList.add(repairOrder)
        }

        return repairList
    }

    fun getUserInformation(id:String): User {
        val json = "{\"id\":\"$id\"}"
        return getUserInformationJson(callApi(json, urlGetUserInformation))
    }

    fun getGroupTag(id:String) {
        val json = "{\"id\":\"$id\"}"
        return getGroupTagJson(callApi(json, urlGetGroupTag))
    }

    fun getPropertyByGroupTag(group_name:String, landlord_id:String): MutableList<Estate> {
        val json = "{\"group_name\":\"$group_name\", \"landlord_id\":\"$landlord_id\"}"
        return getObjectByGroupTagJson(callApi(json, urlGetObjectByGroupTag))
    }

    fun updatePropertyGroupTagInformation(id:String, system_id:String, group_index:Int,
                                          name: String, image: Bitmap): String {
        val imageString = GlobalVariables.imageHelper.getString(image)
        val json = "{\"id\":\"$id\", \"system_id\":\"$system_id\", \"group_index\":$group_index," +
                " \"group\":{\"name\": \"$name\", \"image\": \"$imageString\"} }"
        return getJsonMessage(callApi(json, urlUpdatePropertyGroupTagInformation))
    }

    fun getPropertyByObjectId(object_id:String): Estate {
        val json = "{\"object_id\":\"$object_id\"}"
        return getPropertyByObjectIdJson(callApi(json, urlGetPropertyByObjectId))
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

    fun landlordGetEventInformation(initiate_id:String): MutableList<RepairOrder>{
        val json = "{\"initiate_id\": \"$initiate_id\"}"
        return landlordGetEventInformationJson(callApi(json, urlLandlordGetEventInformation))
    }

//    fun createEvent(initiate_id:String): Boolean{
//        val initiate_id = GlobalVariables.user.id
//        val system_id = GlobalVariables.user.system_id
//        val timestmp = 0 // TODO
//        val type = "maintain" // TODO
//        val status = "nil"
//
//
//        val json = "{\"initiate_id\": \"$initiate_id\"}"
//        return getJsonMessage(callApi(json, urlCreateEvent)) == "No Error"
//    }

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

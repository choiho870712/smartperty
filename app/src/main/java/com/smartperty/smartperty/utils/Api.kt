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
    private val urlCreatePropertyGroupTag = urlDirectory + "grouptagmanagement/creategrouptag"
    private val urlGetPropertyByObjectId = urlDirectory + "propertymanagement/getpropertybyobjectid"
    private val urlUpdateEventInformation = urlDirectory + "eventmanagement/updateeventinformation"
    private val urlGetBarChartByGroupTag = urlDirectory + "reportmanagement/getbarchartbygrouptag"
    private val urlGetPieChartByGroupTag = urlDirectory + "reportmanagement/getpiechartbygrouptag"
    private val urlGetBarChartByObjectType = urlDirectory + "reportmanagement/getbarchartbyobjecttype"
    private val urlGetPieChartByObjectType = urlDirectory + "reportmanagement/getpiechartbyobjecttype"
    private val urlGetBarChartByArea = urlDirectory + "reportmanagement/getbarchartbyarea"
    private val urlGetPieChartByArea = urlDirectory + "reportmanagement/getpiechartbyarea"

    private val urlGetGroupTag = urlDirectory + "grouptagmanagement/getgrouptag"
    private val urlUpdatePropertyGroupTagInformation = urlDirectory + "grouptagmanagement/updategrouptaginformation"

    private val urlCreateContract = urlDirectory + "contractmanagement/createcontract"
    private val urlCreateAccountByLandlord = urlDirectory + "accountmanagement/createaccountbylandlord"
    private val urlCreateProperty = urlDirectory + "propertymanagement/createproperty"
    private val urlGetObjectByGroupTag = urlDirectory + "propertymanagement/getpropertybygrouptag"
    private val urlUploadPropertyEquipment = urlDirectory + "propertymanagement/uploadpropertyequipment"
    private val urlUploadPropertyImage = urlDirectory + "propertymanagement/uploadpropertyimage"
    private val urlCreateEvent = urlDirectory + "eventmanagement/createevent"
    private val urlGetEventInformation = urlDirectory + "eventmanagement/geteventinformation"
    private val urlLandlordGetEventInformation = urlDirectory + "eventmanagement/landlordgeteventinformation"

    fun createContract(contract: Contract): String {
        val object_id = contract.estate!!.objectId
        val tenant_id = contract.tenant!!.id
        val landlord_id = contract.landlord!!.id
        val currency = contract.currency
        val rent = contract.rent.toString()
        val deposit = contract.deposit.toString()
        val start_date = contract.startDate.toString()
        val next_date = contract.nextDate.toString()
        val end_date = contract.endDate.toString()
        val time_left = contract.timeLeft.toString()
        val payment_method = contract.payment_method

        val json = "{\"object_id\": \"$object_id\", \"tenant_id\": \"$tenant_id\"," +
                "\"landlord_id\": \"$landlord_id\", \"currency\": \"$currency\"," +
                "\"rent\": $rent, \"deposit\": $deposit, \"start_date\": $start_date" +
                "\"next_date\": $next_date, \"end_date\": $end_date, \"time_left\": $time_left," +
                "\"payment_method\": \"$payment_method\"}"

        val rawJsonString = callApi(json, urlCreateContract)
        val jsonObject = JSONObject(rawJsonString)
        return try {
            jsonObject.getJSONObject("Items").getString("contract_id")
        }
        catch(e: Exception) {
            "nil"
        }
    }

    fun createAccount(auth: String, object_id: String = "") : Boolean {
        val root_id = GlobalVariables.loginUser.id
        var json = "{\"auth\": \"$auth\", \"root_id\": \"$root_id\""
        if (auth == "tenant") {
            json += ", \"object_id\": \"$object_id\""
        }
        json += "}"
        return getJsonMessage(callApi(json, urlCreateAccountByLandlord)) == "No Error"
    }

    fun createProperty(estate: Estate) {
        val landlord_id = estate.landlord!!.id
        val system_id = estate.landlord!!.system_id
        val group_name = estate.groupName
        val object_name = estate.objectName
        val description = estate.description
        val region = estate.region
        val street = estate.street
        val road = estate.road
        val full_address = estate.fullAddress
        val floor = estate.floor
        val area = estate.area
        val rent = estate.rent
        val parking_space = estate.parkingSpace
        val type = estate.type
        val purchase_price = estate.purchasePrice

        val json = "{\"landlord_id\": \"$landlord_id\", \"system_id\": \"$system_id\", " +
                "\"group_name\": \"$group_name\", \"object_name\": \"$object_name\", " +
                "\"description\": \"$description\", \"region\": \"$region\", " +
                "\"street\": \"$street\", \"road\": \"$road\", " +
                "\"full_address\": \"$full_address\", \"floor\": $floor," +
                "\"area\": \"$area\", \"rent\": $rent," +
                "\"parking_space\": \"$parking_space\", \"type\": \"$type\"," +
                "\"purchase_price\": $purchase_price}"

        val rawJsonString = callApi(json, urlCreateProperty)
        val jsonObject = JSONObject(rawJsonString)
        try {
            estate.objectId =
                jsonObject.getJSONObject("Items").getString("object_id")
            estate.imageList.forEach {
                Thread {
                    uploadPropertyImage(estate.landlord!!.id, estate.objectId, it)
                }.start()
            }
        }
        catch(e:Exception){
            estate.objectId = "nil"
        }
    }

    fun getPropertyByGroupTag(group_name:String, landlord_id:String): MutableList<Estate> {
        val json = "{\"group_name\":\"$group_name\", \"landlord_id\":\"$landlord_id\"}"
        return try {
            getObjectByGroupTagJson(callApi(json, urlGetObjectByGroupTag))
        }
        catch(e:Exception) {
            mutableListOf()
        }
    }

    private fun getObjectByGroupTagJson(rawJsonString:String): MutableList<Estate> {
        val estateList = mutableListOf<Estate>()

        val jsonObject = JSONObject(rawJsonString)
        val items = jsonObject.getJSONArray("Items")
        for (i in 0 until(items.length())) {
            val item = items.getJSONObject(i)
            val information = item.getJSONObject("information")

            val estate = Estate(
                objectId = item.getString("object_id"),
                groupName = item.getString("group_name"),

                area = information.getString("area").toDouble(),
                description = information.getString("description"),
                floor = information.getInt("floor"),
                fullAddress = information.getString("full_address"),
                objectName = information.getString("object_name"),
                parkingSpace = information.getString("parking_space"),
                purchasePrice = information.getInt("purchase_price"),
                region = information.getString("region"),
                rent = information.getInt("rent"),
                road = information.getString("road"),
                rules = information.getString("rules"),
                street = information.getString("street"),
                type = information.getString("type")
            )

            val tenant_id = item.getString("tenant_id")
            Thread {
                estate.tenant = Utils.getUser(tenant_id)
            }.start()

            val landlord_id = item.getString("landlord_id")
            Thread {
                estate.landlord = Utils.getUser(landlord_id)
            }.start()

            val imageList = information.getJSONArray("image")
            for (j in 0 until(imageList.length())) {
                Thread {
                    val image = Utils.getImage(imageList.getString(j))
                    if (image != null) {
                        estate.imageList.add(image)
                    }
                }.start()
            }

            val roomList = information.getJSONArray("equipment")
            val myRoomList = mutableListOf<Room>()
            for (j in 0 until(roomList.length())) {
                val room = roomList.getJSONObject(j)
                val myRoom = Room(name = room.getString("title"))
                val equipmentList = room.getJSONArray("information")
                for (k in 0 until(equipmentList.length())) {
                    val equipment = equipmentList.getJSONObject(k)
                    val myEquipment = Equipment(
                        name = equipment.getString("name"),
                        count = equipment.getInt("count")
                    )
                    myRoom.equipmentList.add(myEquipment)
                    Thread {
                        val image = Utils.getImage(
                            equipment.getJSONArray("image").getString(0))
                        if (image != null) {
                            myEquipment.image = image
                        }
                    }.start()
                }
                myRoomList.add(myRoom)
            }
            estate.roomList = myRoomList

            // TODO get contract
//            val current_contract_id = item.getString("current_contract_id")

            // TODO get attraction list
//            val attractionList = information.getJSONArray("attraction")
//            for (j in 0 until(attractionList.length()))
//                estate.attractionList.add(attractionList.getString(j))

            // TODO get contract history
//            val contractHistoryIdList = item.getJSONArray("contract_history")
//            for (j in 0 until(contractHistoryIdList.length()))
//                estate.contractHistoryIdList.add(contractHistoryIdList.getString(j))

            // important!!
            // avoid Utils.getRepairOrder() not found this estate
            // when calling getEventInformation() api
            Utils.addEstateToEstateList(estate)

            val eventHistoryIdList = item.getJSONArray("event_history")
            for (j in 0 until(eventHistoryIdList.length())) {
                estate.repairList.add(
                    Utils.getRepairOrder(eventHistoryIdList.getString(j))!!
                )
            }

            estateList.add(estate)
        }
        return estateList
    }

    fun uploadPropertyEquipment(landlord_id: String, object_id: String, roomList: MutableList<Room>): Boolean {
        var json = "{\"landlord_id\": \"$landlord_id\", \"object_id\": \"$object_id\", " +
                "\"equipment\": ["
        roomList.forEachIndexed { index, room ->
            val roomName = room.name

            if (index > 0)
                json += ","

            var isFirst = true
            val equipmentList = room.equipmentList
            json += "{\"title\" : \"$roomName\", \"information\" : ["
            equipmentList.forEach {
                val equipmentName = it.name
                val equipmentCount = it.count.toString()

                if (isFirst) isFirst = false
                else json += ","

                json += "{\"name\" : \"$equipmentName\", \"count\" : $equipmentCount, " +
                        "\"image\" : ["
                if (it.image != null) {
                    val imageString = GlobalVariables.imageHelper.getString(it.image!!)
                    json += "\"$imageString\""
                }
                json += "]}"
            }
            json += "]}"
        }
        json += "]}"
        return getJsonMessage(callApi(json, urlUploadPropertyEquipment)) == "No Error"
    }

    fun uploadPropertyImage(landlord_id: String, object_id: String, image: Bitmap): Boolean {
        val imageString = GlobalVariables.imageHelper.getString(image)
        val json = "{\"landlord_id\": \"$landlord_id\", \"object_id\": \"$object_id\", " +
                "\"image\":[\"$imageString\"]}"
        return getJsonMessage(callApi(json, urlUploadPropertyImage)) == "No Error"
    }

    fun createEvent(repairOrder: RepairOrder){
        val initiate_id = GlobalVariables.loginUser.id
        val system_id = GlobalVariables.loginUser.system_id
        val timestmp = repairOrder.timestamp.toString()
        val type = repairOrder.type
        val status = repairOrder.status
        val description = repairOrder.description
        val date = repairOrder.date
        val object_id = repairOrder.estate!!.objectId

        var json = "{\"initiate_id\": \"$initiate_id\", \"system_id\": \"$system_id\", " +
                "\"timestmp\": $timestmp, \"type\": \"$type\", \"status\": \"$status\"," +
                "\"object_id\" : \"$object_id\",\"participant\": ["
        // TODO add participant
//        if (tenant.id.isNotEmpty() && tenant.id != "nil") {
//            val tenantId = tenant.id
//            json += "{\"id\": \"$tenantId\", \"auth\": \"tenant\"}"
//        }
//        if (tenant.id.isNotEmpty() && tenant.id != "nil" && technician.id.isNotEmpty() && technician.id != "nil") {
//            json += ","
//        }
//        if (technician.id.isNotEmpty() && technician.id != "nil") {
//            val technicianId = technician.id
//            json += "{\"id\": \"$technicianId\", \"auth\": \"technician\"}"
//        }
        json += "],\"information\": {\"description\": \"$description\", " +
                "\"date\": \"$date\", \"dynamic_status\": ["
        json += "]}}"

        repairOrder.event_id =
        try {
            JSONObject(callApi(json, urlCreateEvent))
                .getJSONObject("Items")
                .getString("event_id")
        }
        catch (e: Exception) {
            "nil"
        }
    }

    fun getEventInformation(event_id:String): RepairOrder {
        val json = "{\"event_id\": \"$event_id\"}"
        return try {
            getEventInformationJson(callApi(json, urlGetEventInformation))
        }
        catch (e:Exception) {
            RepairOrder()
        }
    }

    private fun getEventInformationJson(rawJsonString:String): RepairOrder {
        val jsonObject = JSONObject(rawJsonString)
        val item = jsonObject.getJSONArray("Items").getJSONObject(0)
        return getEventInformationItemJson(item)
    }

    private fun getEventInformationItemJson(item:JSONObject): RepairOrder {
        val repairOrder = RepairOrder()
        val information = item.getJSONObject("information")
        val dynamic_status = information.getJSONArray("dynamic_status")
        repairOrder.timestamp = item.getLong("timestmp")
        repairOrder.status = item.getString("status")
        repairOrder.event_id = item.getString("event_id")
        repairOrder.type = item.getString("type")
        repairOrder.date = information.getString("date")
        repairOrder.description = information.getString("description")

        val initiate_id = item.getString("initiate_id")
        Thread {
            repairOrder.creator = Utils.getUser(initiate_id)
        }.start()

        val object_id = item.getString("object_id")
        Thread {
            repairOrder.estate = Utils.getEstate(object_id)
        }.start()

        for (j in 0 until(dynamic_status.length())) {
            val dynamic_status_item = dynamic_status.getJSONObject(j)
            val repairOrderPost = RepairOrderPost(
                createDateTime = dynamic_status_item.getString("date"),
                message = dynamic_status_item.getString("description")
            )
            repairOrderPost.sender = Utils.getUser(dynamic_status_item.getString("sender_id"))!!

            val imageUrlListObject = dynamic_status_item.getJSONArray("image")
            for (k in 0 until(imageUrlListObject.length())) {
                Thread {
                    val image = Utils.getImage(imageUrlListObject.getString(k))
                    if (image!= null)
                        repairOrderPost.imageList.add(image)
                }.start()
            }

            repairOrder.postList.add(repairOrderPost)
        }

        // TODO get participant
//        val participant = item.getJSONArray("participant")
//        for (j in 0 until(participant.length())) {
//            val participantItem = participant.getJSONObject(j)
//
//            when (participantItem.getString("auth")) {
//                "technician" -> {
//                    repairOrder.plumber_id = participantItem.getString("id")
//                }
//                "tenant" -> {
//                    repairOrder.tenant_id = participantItem.getString("id")
//                }
//                else -> {
//                }
//            }
//        }

        return repairOrder
    }

    fun landlordGetEventInformation(initiate_id:String): MutableList<RepairOrder>{
        val json = "{\"initiate_id\": \"$initiate_id\"}"
        return try {
            landlordGetEventInformationJson(callApi(json, urlLandlordGetEventInformation))
        }
        catch (e: Exception) {
            mutableListOf()
        }
    }

    private fun landlordGetEventInformationJson(rawJsonString:String): MutableList<RepairOrder> {
        val repairList = mutableListOf<RepairOrder>()

        val jsonObject = JSONObject(rawJsonString)
        val items = jsonObject.getJSONArray("Items")
        for (i in 0 until(items.length())) {
            val item = items.getJSONObject(i)
            val repairOrder = getEventInformationItemJson(item)
            repairList.add(repairOrder)
        }

        return repairList
    }












    private fun getBarChartByGroupTagJson(rawJsonString:String): ChartData {
        val chartData = ChartData(type = ChartDataType.BAR_CHART)

        val jsonObject = JSONObject(rawJsonString)
        val items = jsonObject.getJSONArray("Items")
        for (i in 0 until(items.length())) {
            val item = items.getJSONObject(i)
            chartData.dataList.add(
                ChartDataPair(
                    tag = item.getString("group_name"),
                    value = item.getInt("rent"),
                    value2 = item.getDouble("return_on_investment")
                )
            )
        }
        return chartData
    }

    private fun getPieChartByGroupTagJson(rawJsonString:String): ChartData {
        val chartData = ChartData(type = ChartDataType.PIE_CHART)

        val jsonObject = JSONObject(rawJsonString)
        val items = jsonObject.getJSONArray("Items")
        for (i in 0 until(items.length())) {
            val item = items.getJSONObject(i)
            chartData.dataList.add(
                ChartDataPair(
                    tag = item.getString("group_name"),
                    value = item.getInt("counter")
                )
            )
        }
        return chartData
    }

    private fun getBarChartByObjectTypeJson(rawJsonString:String): ChartData {
        val chartData = ChartData(type = ChartDataType.BAR_CHART)

        val jsonObject = JSONObject(rawJsonString)
        val items = jsonObject.getJSONArray("Items")
        for (i in 0 until(items.length())) {
            val item = items.getJSONObject(i)
            chartData.dataList.add(
                ChartDataPair(
                    tag = item.getString("type"),
                    value = item.getInt("rent"),
                    value2 = item.getDouble("return_on_investment")
                )
            )
        }
        return chartData
    }

    private fun getPieChartByObjectTypeJson(rawJsonString:String): ChartData {
        val chartData = ChartData(type = ChartDataType.PIE_CHART)

        val jsonObject = JSONObject(rawJsonString)
        val items = jsonObject.getJSONArray("Items")
        for (i in 0 until(items.length())) {
            val item = items.getJSONObject(i)
            chartData.dataList.add(
                ChartDataPair(
                    tag = item.getString("type"),
                    value = item.getInt("counter")
                )
            )
        }
        return chartData
    }

    private fun getBarChartByAreaJson(rawJsonString:String): ChartData {
        val chartData = ChartData(type = ChartDataType.BAR_CHART)

        val jsonObject = JSONObject(rawJsonString)
        val items = jsonObject.getJSONArray("Items")
        for (i in 0 until(items.length())) {
            val item = items.getJSONObject(i)
            chartData.dataList.add(
                ChartDataPair(
                    tag = item.getString("area"),
                    value = item.getInt("rent"),
                    value2 = item.getDouble("return_on_investment")
                )
            )
        }
        return chartData
    }

    private fun getPieChartByAreaJson(rawJsonString:String): ChartData {
        val chartData = ChartData(type = ChartDataType.PIE_CHART)

        val jsonObject = JSONObject(rawJsonString)
        val items = jsonObject.getJSONArray("Items")
        for (i in 0 until(items.length())) {
            val item = items.getJSONObject(i)
            chartData.dataList.add(
                ChartDataPair(
                    tag = item.getString("area"),
                    value = item.getInt("counter")
                )
            )
        }
        return chartData
    }

    // TODO check
    private fun getPropertyByObjectIdJson(rawJsonString:String): Estate {
        val jsonObject = JSONObject(rawJsonString)
        val item = jsonObject.getJSONObject("Items")
        val eventHistoryIdList = item.getJSONArray("event_history")
        val contractHistoryIdList = item.getJSONArray("contract_history")
        val information = item.getJSONObject("information")
        val imageList = information.getJSONArray("image")
        val estate = Estate(
            objectId = item.getString("object_id"),
            area = information.getString("area").toDouble(),
            fullAddress = information.getString("address"),
            road = information.getString("road"),
            street = information.getString("street"),
            objectName = information.getString("object_name"),
            region = information.getString("district"),
            contract = Contract(
                tenant = User(
                    id = item.getString("tenant_id"),
                    name = item.getString("tenant_id")
                ),
                landlord = User(
                    id = item.getString("landlord_id"),
                    name = item.getString("landlord_id")
                )
            ),
            parkingSpace = information.getString("parking_space"),
            description = information.getString("description"),
            floor = information.getInt("floor"),
            type = information.getString("type"),
            purchasePrice = information.getInt("purchase_price")
        )

        return estate
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

            GlobalVariables.loginUser.estateDirectory.add(newGroup)

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
        user.auth = items.getString("auth")
        user.permissions = items.getString("permissions")
        user.system_id = items.getString("system_id")
        user.id = items.getString("id")

        // information
        val information = items.getJSONObject("information")
        user.name = information.getString("name")
        user.email = information.getString("mail")
        user.cellPhone = information.getString("phone")
        user.sex = information.getString("sex")
        user.annual_income = information.getString("annual_income")
        user.industry = information.getString("industry")
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

    fun getUserInformation(id:String): User {
        val json = "{\"id\":\"$id\"}"
        return getUserInformationJson(callApi(json, urlGetUserInformation))
    }

    fun getGroupTag(id:String) {
        val json = "{\"id\":\"$id\"}"
        return getGroupTagJson(callApi(json, urlGetGroupTag))
    }

    fun updatePropertyGroupTagInformation(id:String, system_id:String, group_index:Int,
                                          name: String, image: Bitmap): String {
        val imageString = GlobalVariables.imageHelper.getString(image)
        val json = "{\"id\":\"$id\", \"system_id\":\"$system_id\", \"group_index\":$group_index," +
                " \"group\":{\"name\": \"$name\", \"image\": \"$imageString\"} }"
        return getJsonMessage(callApi(json, urlUpdatePropertyGroupTagInformation))
    }

    fun getPropertyByObjectId(object_id:String): Estate? {
        val json = "{\"object_id\":\"$object_id\"}"
        return try {
            getPropertyByObjectIdJson(callApi(json, urlGetPropertyByObjectId))
        } catch (e:Exception) { null }
    }

    fun getBarChartByGroupTag(landlord_id:String): ChartData {
        val json = "{\"landlord_id\":\"$landlord_id\"}"
        return try {
            getBarChartByGroupTagJson(callApi(json, urlGetBarChartByGroupTag))
        }
        catch (e:Exception){ ChartData() }
    }
    fun getPieChartByGroupTag(landlord_id:String): ChartData {
        val json = "{\"landlord_id\":\"$landlord_id\"}"
        return try {
            getPieChartByGroupTagJson(callApi(json, urlGetPieChartByGroupTag))
        } catch (e:Exception){ ChartData() }
    }
    fun getBarChartByObjectType(landlord_id:String): ChartData {
        val json = "{\"landlord_id\":\"$landlord_id\"}"
        return try {
            getBarChartByObjectTypeJson(callApi(json, urlGetBarChartByObjectType))
        } catch (e:Exception){ ChartData() }
    }
    fun getPieChartByObjectType(landlord_id:String): ChartData {
        val json = "{\"landlord_id\":\"$landlord_id\"}"
        return try {
            getPieChartByObjectTypeJson(callApi(json, urlGetPieChartByObjectType))
        } catch (e:Exception){ ChartData() }
    }
    fun getBarChartByArea(landlord_id:String): ChartData {
        val json = "{\"landlord_id\":\"$landlord_id\"}"
        return try {
            getBarChartByAreaJson(callApi(json, urlGetBarChartByArea))
        } catch (e:Exception){ ChartData() }
    }
    fun getPieChartByArea(landlord_id:String): ChartData {
        val json = "{\"landlord_id\":\"$landlord_id\"}"
        return try {
            getPieChartByAreaJson(callApi(json, urlGetPieChartByArea))
        } catch (e:Exception){ ChartData() }
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

    fun updateEventInformation(
        landlord_id: String,
        id:String, title:String, repairOrderPost: RepairOrderPost): Boolean {
        val sender_id = repairOrderPost.sender.id
        val description = repairOrderPost.message
        val date = repairOrderPost.createDateTime
        var json = "{\"landlord_id\": \"$landlord_id\"," +
                " \"event_id\": \"$id\", \"description\": \"$title\"," +
                " \"dynamic_status\":{\"sender_id\": \"$sender_id\", \"description\": \"$description\", " +
                "\"date\": \"$date\", \"image\": ["

        var isFirst = true
        repairOrderPost.imageList.forEach {
            if (isFirst) isFirst = false
            else json += ","
            json += "\""+GlobalVariables.imageHelper.getString(it)+"\""
        }

        json += "]}}"
        return getJsonMessage(callApi(json, urlUpdateEventInformation)) == "No Error"
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

        var responseString = ""
        var needToCallAgain = false
        var isSuccess = false

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(request: Request, e: IOException) {
                responseString = e.message!!
                needToCallAgain = true
            }

            @Throws(IOException::class)
            override fun onResponse(response: Response) {
                responseString = response.body().string()
                isSuccess = true
            }
        })

        while (!isSuccess) {
            if (needToCallAgain) {
                needToCallAgain = false
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(request: Request, e: IOException) {
                        responseString = e.message!!
                        needToCallAgain = true
                    }

                    @Throws(IOException::class)
                    override fun onResponse(response: Response) {
                        responseString = response.body().string()
                        isSuccess = true
                    }
                })
            }

            Thread.sleep(500)
        }

        return responseString
    }


}

package com.smartperty.smartperty.landlord.menu.estate

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.Estate
import com.smartperty.smartperty.data.User
import com.smartperty.smartperty.repair.ImageListAdapter
import com.smartperty.smartperty.repair.RepairList2Adapter
import com.smartperty.smartperty.tools.TimeUtil
import com.smartperty.smartperty.utils.GlobalVariables
import com.smartperty.smartperty.utils.Utils
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.fragment_contract_create.view.*
import kotlinx.android.synthetic.main.fragment_estate.view.*
import kotlinx.android.synthetic.main.fragment_estate.view.recycler_image
import kotlinx.android.synthetic.main.fragment_estate.view.textView_object_item_address
import kotlinx.android.synthetic.main.fragment_estate.view.textView_object_item_content
import kotlinx.android.synthetic.main.fragment_estate.view.textView_object_item_parking_sapce
import kotlinx.android.synthetic.main.fragment_estate.view.textView_object_item_phone
import kotlinx.android.synthetic.main.fragment_estate.view.textView_object_item_square_ft
import kotlinx.android.synthetic.main.fragment_estate.view.textView_object_item_tenant_name
import java.util.*

class EstateFragment : Fragment() {

    private lateinit var root: View
    private var imageList: MutableList<Bitmap> = mutableListOf()
    private lateinit var imageListAdapter: ImageListAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_estate, container, false)

        GlobalVariables.imageListUsage = "read"

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setTitle(GlobalVariables.estate.objectName)

        fillInformation()

        if (GlobalVariables.loginUser.permission.property == "A")
            GlobalVariables.toolBarUtils.setEditButtonVisibility(true)

        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.button_edit -> {
                    root.findNavController().navigate(
                        R.id.action_estateFragment_to_estateCreateFragment
                    )
                    true
                }
                else -> false
            }
        }

        setEditable(false)
        setButtonEnable(true)

        if (GlobalVariables.estate.tenant != null) {
            if (GlobalVariables.loginUser.permission.staff != "N") {
                root.textView_object_item_tenant_name.setOnClickListener {
                    GlobalVariables.personnel = GlobalVariables.estate.tenant!!
                    GlobalVariables.personnelUserInfoUsage = "read"
                    root.findNavController().navigate(
                        R.id.action_estateFragment_to_personnelFragment
                    )
                }
            }
        }
        else {
            root.textView_object_item_tenant_name.text = "新增房客"
            if (GlobalVariables.loginUser.permission.property == "A")
                root.textView_object_item_tenant_name.setOnClickListener {
                    GlobalVariables.personnel = User()
                    GlobalVariables.personnel.auth = "tenant"
    //                    GlobalVariables.personnelUserInfoUsage = "create"
                    root.findNavController().navigate(
                        R.id.action_estateFragment_to_personnelAddFragment
                    )
                }
        }

        imageListAdapter = ImageListAdapter(requireActivity(), root, imageList)
        root.recycler_image.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false )
            adapter = imageListAdapter
        }

        if (GlobalVariables.loginUser.permission.event != "N") {
            root.recycler_repair.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true )
                adapter = RepairList2Adapter(
                    requireActivity(), root,
                    GlobalVariables.estate.repairList
                )
            }
        }

        root.button_set_attraction.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_estateFragment_to_tenantAttractionsNearbyFragment2
            )
        }
        root.button_set_equipment.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_estateFragment_to_tenantEquipmentManualFragment2
            )
        }
        root.button_set_housing_rules.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_estateFragment_to_tenantHosingRulesFragment2
            )
        }

        root.button_view_contract_pdf.visibility = View.GONE
        root.button_upload_contract.visibility = View.GONE
        root.button_create_contract.visibility = View.GONE

        if (GlobalVariables.estate.contract != null) {
            if (GlobalVariables.estate.contract!!.pdfString.isEmpty() &&
                GlobalVariables.estate.contract!!.textString.isEmpty() &&
                GlobalVariables.estate.contract!!.jpgBitmapList.isEmpty()) {

                if (GlobalVariables.loginUser.permission.contract == "A") {
                    root.button_upload_contract.visibility = View.VISIBLE
                    root.button_upload_contract.setOnClickListener {
                        root.findNavController().navigate(
                            R.id.action_estateFragment_to_contractUploadFragment
                        )
                    }
                }
            }
            else {
                if (GlobalVariables.loginUser.permission.contract != "N") {
                    root.button_view_contract_pdf.visibility = View.VISIBLE
                    root.button_view_contract_pdf.setOnClickListener {
                        root.findNavController().navigate(
                            R.id.action_estateFragment_to_contractPdfShowFragment
                        )
                    }
                }
            }
        }
        else if (GlobalVariables.estate.tenant != null) {
            if (GlobalVariables.loginUser.permission.contract == "A") {
                root.button_create_contract.visibility = View.VISIBLE
                root.button_create_contract.setOnClickListener {
                    root.findNavController().navigate(
                        R.id.action_estateFragment_to_contractCreateFragment
                    )
                }
            }
        }

        return root
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    private fun fillInformation() {
        root.textView_object_item_address.setText(
            GlobalVariables.estate.region +
                    GlobalVariables.estate.road +
                    GlobalVariables.estate.street +
                    GlobalVariables.estate.fullAddress
        )
        root.textView_object_item_floor.setText(
            GlobalVariables.estate.floor.toString())
        root.textView_object_item_square_ft.setText(
            GlobalVariables.estate.area.toString())
        root.textView_object_item_parking_sapce.setText(
            GlobalVariables.estate.parkingSpace)
        root.textView_object_item_tenant_name.setText(
            GlobalVariables.estate.tenant?.name ?: "")
        root.textView_object_item_content.setText(
            GlobalVariables.estate.description)
        if (GlobalVariables.estate.tenant != null)
        root.textView_object_item_phone.setText(
            GlobalVariables.estate.tenant!!.cellPhone)

        imageList.clear()
        if (GlobalVariables.estate.imageList.isNotEmpty())
            imageList.addAll(GlobalVariables.estate.imageList)
        else
            imageList.add(requireActivity().resources.getDrawable(R.drawable.empty_house).toBitmap())

        if (GlobalVariables.loginUser.permission.contract != "N" &&
            GlobalVariables.estate.contract != null) {
            root.text_object_item_rent.setText(
                GlobalVariables.estate.contract!!.rent.toString())
            root.text_object_item_deposit.setText(
                GlobalVariables.estate.contract!!.deposit.toString())
            root.text_object_item_status.text = GlobalVariables.estate.contract!!.getStatusString()
            root.text_object_item_next_date.setText(
                TimeUtil.StampToDate(GlobalVariables.estate.contract!!.getNextDate(), Locale.TAIWAN))
            root.text_object_item_start_date.setText(
                TimeUtil.StampToDate(GlobalVariables.estate.contract!!.startDate, Locale.TAIWAN))
            root.text_object_item_end_date.setText(
                TimeUtil.StampToDate(GlobalVariables.estate.contract!!.endDate, Locale.TAIWAN))

            val paymentMethod =
                when (GlobalVariables.estate.contract!!.payment_method) {
                    "Permonth" -> {
                        "月繳"
                    }
                    "Perseason" -> {
                        "季繳"
                    }
                    "Perhalfyear" -> {
                        "半年繳"
                    }
                    "Peryear" -> {
                        "年繳"
                    }
                    else -> {
                        "nil"
                    }
                }

            root.text_object_item_payment_method.setText(paymentMethod)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setEditable(editable: Boolean) {
        //GlobalVariables.toolBarUtils.setEditButtonVisibility(!editable)
        GlobalVariables.toolBarUtils.setCancelButtonVisibility(editable)
        GlobalVariables.toolBarUtils.setSubmitButtonVisibility(editable)

        if (editable) {
            if (GlobalVariables.estate.objectId.isEmpty())
                GlobalVariables.toolBarUtils.setTitle("")
            else
                GlobalVariables.toolBarUtils.setTitle("新增物件")
        }
        else {
            GlobalVariables.toolBarUtils.setTitle(GlobalVariables.estate.objectName)
        }

        var background =
            if (editable)
                resources.getDrawable(R.drawable.style_hollow_white_smoke)
            else
                resources.getDrawable(R.drawable.style_empty)

        root.textView_object_item_address.background = background
        root.textView_object_item_floor.background = background
        root.textView_object_item_square_ft.background = background
        root.textView_object_item_parking_sapce.background = background
        root.textView_object_item_content.background = background

        if (editable) {
            root.textView_object_item_address.inputType = InputType.TYPE_CLASS_TEXT
            root.textView_object_item_floor.inputType = InputType.TYPE_CLASS_NUMBER
            root.textView_object_item_square_ft.inputType = InputType.TYPE_CLASS_NUMBER
            root.textView_object_item_parking_sapce.inputType = InputType.TYPE_CLASS_TEXT
            root.textView_object_item_content.inputType = InputType.TYPE_CLASS_TEXT
        }
        else {
            root.textView_object_item_address.inputType = InputType.TYPE_NULL
            root.textView_object_item_floor.inputType = InputType.TYPE_NULL
            root.textView_object_item_square_ft.inputType = InputType.TYPE_NULL
            root.textView_object_item_parking_sapce.inputType = InputType.TYPE_NULL
        }
    }

    private fun storeInformation() {
        GlobalVariables.estate.fullAddress = root.textView_object_item_address.text.toString()
        GlobalVariables.estate.floor = root.textView_object_item_floor.text.toString()
        GlobalVariables.estate.area = root.textView_object_item_square_ft.text.toString().toInt()
        GlobalVariables.estate.parkingSpace = root.textView_object_item_parking_sapce.text.toString()
        GlobalVariables.estate.description = root.textView_object_item_content.text.toString()

        GlobalVariables.estate.imageList.clear()
        GlobalVariables.estate.imageList.addAll(imageList)

        if (GlobalVariables.estate.objectId.isEmpty()) {
            GlobalVariables.estate.objectId = "nil"
            GlobalVariables.estate.groupName = GlobalVariables.estateFolder.title
            GlobalVariables.estate.landlord = GlobalVariables.loginUser
            GlobalVariables.estateFolder.list.add(GlobalVariables.estate)

            Utils.createEstate(GlobalVariables.estate)
        }
    }

    private fun setButtonEnable(enable: Boolean) {
        root.textView_object_item_tenant_name.isEnabled = enable
        root.button_set_attraction.isEnabled = enable
        root.button_set_equipment.isEnabled = enable
        root.button_set_housing_rules.isEnabled = enable
        root.button_create_contract.isEnabled = enable
    }
}
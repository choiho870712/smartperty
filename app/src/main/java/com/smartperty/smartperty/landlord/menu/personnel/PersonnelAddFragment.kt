package com.smartperty.smartperty.landlord.menu.personnel

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.ChartData
import com.smartperty.smartperty.data.Contract
import com.smartperty.smartperty.data.Estate
import com.smartperty.smartperty.data.User
import com.smartperty.smartperty.utils.GlobalVariables
import com.smartperty.smartperty.utils.Utils
import kotlinx.android.synthetic.main.activity_tenant.*
import kotlinx.android.synthetic.main.fragment_personnel_add.view.*
import kotlinx.android.synthetic.main.fragment_personnel_user_info.view.*
import kotlinx.android.synthetic.main.fragment_repair_order_create.view.spinner

class PersonnelAddFragment : Fragment() {

    private lateinit var root: View
    private lateinit var authChinese: String
    private lateinit var permissionStringList: MutableList<String>
    private var isKnownEstate = false

    companion object {
        private lateinit var buttonSelectProperty: Button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (GlobalVariables.personnel.auth == "tenant") {
            authChinese = "租客"
            isKnownEstate = true
        }
        else {
            authChinese = "點擊選擇"
        }
        permissionStringList = mutableListOf()
        permissionStringList.add("可修改")
        permissionStringList.add("可修改")
        permissionStringList.add("可修改")
        permissionStringList.add("可修改")
        permissionStringList.add("可修改")
        permissionStringList.add("可修改")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_personnel_add, container, false)

        buttonSelectProperty = root.button_select_property

        if (isKnownEstate) {
            root.button_select_property.isEnabled = false
            root.button_select_auth.isEnabled = false
        }

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)

        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.button_submit -> {
                    // setup dialog builder
                    val builder = android.app.AlertDialog.Builder(requireActivity())
                    builder.setTitle("是否新增人員？")

                    builder.setPositiveButton("是") { _, _ ->

                        GlobalVariables.personnel.auth =
                            when(root.button_select_auth.text.toString()) {
                                "租客"-> {
                                    "tenant"
                                }
                                "維護"-> {
                                    "technician"
                                }
                                "會計"-> {
                                    "accountant"
                                }
                                "代理"-> {
                                    "agent"
                                }
                                "老闆"-> {
                                    "boss"
                                }
                                "物管"-> {
                                    "propertymanger"
                                }
                                "仲介"-> {
                                    "intermediary"
                                }
                                else -> {
                                    ""
                                }
                            }

                        GlobalVariables.personnel.permission.property =
                            getPermissionSelected(root.button_select_permission_object)
                        GlobalVariables.personnel.permission.contract =
                            getPermissionSelected(root.button_select_permission_contract)
                        GlobalVariables.personnel.permission.data =
                            getPermissionSelected(root.button_select_permission_data)
                        GlobalVariables.personnel.permission.payment =
                            getPermissionSelected(root.button_select_permission_paymant)
                        GlobalVariables.personnel.permission.event =
                            getPermissionSelected(root.button_select_permission_event)
                        GlobalVariables.personnel.permission.staff =
                            getPermissionSelected(root.button_select_permission_staff)

                        GlobalVariables.personnel.name = root.editText_name.text.toString()
                        GlobalVariables.personnel.cellPhone = root.editTextPhone.text.toString()

                        if (GlobalVariables.personnel.auth == "tenant")
                            GlobalVariables.estate.tenant = GlobalVariables.personnel
                        Utils.createAccount(
                            GlobalVariables.personnel,GlobalVariables.estate.objectId)

                        root.findNavController().navigateUp()
                    }

                    // create dialog and show it
                    requireActivity().runOnUiThread{
                        val dialog = builder.create()
                        dialog.show()
                    }

                    true
                }
                else -> false
            }
        }

        //createSpinner()

        if (GlobalVariables.estate.objectId != "nil")
            root.button_select_property.text = GlobalVariables.estate.objectName

        root.button_select_auth.text = authChinese

        buttonSelectProperty.setOnClickListener {
            GlobalVariables.propertySelectorUsage = "not rented"
            root.findNavController().navigate(
                R.id.action_personnelAddFragment_to_choosePropertyFragment
            )
        }

        root.button_select_auth.setOnClickListener {
            val builderSingle: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val arrayAdapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.select_dialog_singlechoice
            )
            arrayAdapter.add("租客")
            arrayAdapter.add("維護")
            arrayAdapter.add("會計")
            arrayAdapter.add("代理")
            arrayAdapter.add("老闆")
            arrayAdapter.add("物管")
            arrayAdapter.add("仲介")

            builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
            builderSingle.setAdapter(arrayAdapter) { _, which ->
                val strName = arrayAdapter.getItem(which)
                root.button_select_auth.text = strName
                authChinese = strName!!
                if (authChinese == "租客") {
                    root.button_select_property.visibility = View.VISIBLE
                    root.textView_property_title.visibility = View.VISIBLE
                }
                else {
                    root.button_select_property.visibility = View.GONE
                    root.textView_property_title.visibility = View.GONE
                }
            }
            builderSingle.show()
        }

        setSelectPermissionButtonOnClickListener(root.button_select_permission_object, 0)
        setSelectPermissionButtonOnClickListener(root.button_select_permission_contract, 1)
        setSelectPermissionButtonOnClickListener(root.button_select_permission_data, 2)
        setSelectPermissionButtonOnClickListener(root.button_select_permission_paymant, 3)
        setSelectPermissionButtonOnClickListener(root.button_select_permission_event, 4)
        setSelectPermissionButtonOnClickListener(root.button_select_permission_staff, 5)

        return root
    }

    private fun setSelectPermissionButtonOnClickListener(button: Button, permissionStringIndex: Int) {
        button.text = permissionStringList[permissionStringIndex]
        button.setOnClickListener {
            val builderSingle: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val arrayAdapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.select_dialog_singlechoice
            )
            arrayAdapter.add("可修改")
            arrayAdapter.add("可查看")
            arrayAdapter.add("不可查看")

            builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
            builderSingle.setAdapter(arrayAdapter) { _, which ->
                val strName = arrayAdapter.getItem(which)
                button.text = strName
                permissionStringList[permissionStringIndex] = strName!!
            }
            builderSingle.show()
        }
    }

    private fun getPermissionSelected(button: Button): String {
        return when(button.text) {
            "可修改" -> {
                "A"
            }
            "可查看" -> {
                "R"
            }
            else -> {
                "N"
            }
        }
    }

    class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            val authArray = mutableListOf("tenant", "technician", "accountant", "agent"
                , "boss", "propertymanger", "intermediary")
            GlobalVariables.personnel.auth = authArray[pos]

            if (pos > 0) {
                buttonSelectProperty.visibility = View.GONE
            }
            else {
                buttonSelectProperty.visibility = View.VISIBLE
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // Another interface callback
        }
    }

    private fun createSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.personnel_type_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            root.spinner.adapter = adapter
        }

        root.spinner.onItemSelectedListener = SpinnerActivity()
    }

}
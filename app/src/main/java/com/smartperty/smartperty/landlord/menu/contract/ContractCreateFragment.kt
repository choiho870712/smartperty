package com.smartperty.smartperty.landlord.menu.contract

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.Contract
import com.smartperty.smartperty.tools.TimeUtil
import com.smartperty.smartperty.utils.GlobalVariables
import com.smartperty.smartperty.utils.Utils
import kotlinx.android.synthetic.main.activity_tenant.*
import kotlinx.android.synthetic.main.fragment_contract_create.view.*
import java.util.*

class ContractCreateFragment : Fragment() {

    private lateinit var root: View
    private lateinit var datePickerDialog_start: DatePickerDialog
    private var startYear = 0
    private var startMonth = 0
    private var startDay = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalVariables.contract = Contract()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_contract_create, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)

        if (GlobalVariables.estate.objectId != "nil") {
            GlobalVariables.contract.estate = GlobalVariables.estate
            GlobalVariables.contract.landlord = GlobalVariables.estate.landlord

            if (GlobalVariables.estate.tenant != null) {
                GlobalVariables.contract.tenant = GlobalVariables.estate.tenant
            }
        }

        root.edit_create_contract_rent.setText(GlobalVariables.contract.rent.toString())
        root.edit_create_contract_deposit.setText(GlobalVariables.contract.deposit.toString())

        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.button_submit -> {
                    // setup dialog builder
                    val builder = android.app.AlertDialog.Builder(requireActivity())
                    builder.setTitle("是否送出合約？")

                    builder.setPositiveButton("是") { _, _ ->
                        GlobalVariables.contract.payment_method =
                            when (root.button_create_contract_select_pay_method.text.toString()) {
                                "月繳" -> {
                                    "monthly"
                                }
                                "季繳" -> {
                                    "monthly"
                                }
                                "半年繳" -> {
                                    "monthly"
                                }
                                "年繳" -> {
                                    "monthly"
                                }
                                else -> {
                                    "monthly"
                                }
                            }
                        GlobalVariables.contract.startDate =
                            TimeUtil.DateToStamp(
                                root.button_create_contract_select_start_date.text.toString(),
                                Locale.TAIWAN
                            )
                        GlobalVariables.contract.endDate =
                            TimeUtil.DateToStamp(
                                root.textView_create_contract_endDate.text.toString(),
                                Locale.TAIWAN
                            )
                        GlobalVariables.contract.rent =
                            root.edit_create_contract_rent.text.toString().toInt()
                        GlobalVariables.contract.deposit =
                            root.edit_create_contract_deposit.text.toString().toInt()
                        GlobalVariables.contract.currency =
                            root.button_create_contract_select_currency.text.toString()
                        GlobalVariables.contract.timeLeft =
                            root.edit_create_contract_pay_times.text.toString().toInt()

                        Utils.createContract(GlobalVariables.contract)

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

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        datePickerDialog_start = DatePickerDialog(
            requireContext(),
            { _, y, m, d ->
                val m2 = m+1
                val dateString = "$y/$m2/$d"
                startYear = y
                startMonth = m2
                startDay = d
                root.button_create_contract_select_start_date.text = dateString
            },
            year, month, day
        )

        root.button_create_contract_select_start_date.setOnClickListener {
            datePickerDialog_start.show()
        }

        root.edit_create_contract_pay_times.addTextChangedListener {
            val payTimes = it.toString().toInt()
            val endDate =
                when(GlobalVariables.contract.payment_method) {
                    "monthly" -> {
                        val endMonth = (startMonth + payTimes-1)%12 + 1
                        val endYear = (startMonth + payTimes-1)/12 + startYear
                        "$endYear/$endMonth/$startDay"
                    }
                    else -> {
                        ""
                    }
                }
            root.textView_create_contract_endDate.text = endDate
        }

        root.button_create_contract_select_currency.text = "TWD"

        root.button_create_contract_select_pay_method.setOnClickListener {
            val builderSingle: AlertDialog.Builder = AlertDialog.Builder(activity)

            val arrayAdapter = ArrayAdapter<String>(
                requireActivity(),
                android.R.layout.select_dialog_singlechoice
            )
            arrayAdapter.add("月繳")
            arrayAdapter.add("季繳")
            arrayAdapter.add("半年繳")
            arrayAdapter.add("年繳")

            builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }

            builderSingle.setAdapter(arrayAdapter) { _, which ->
                val strName = arrayAdapter.getItem(which)
                root.button_create_contract_select_pay_method.text = strName
            }
            builderSingle.show()
        }

        return root
    }

}
package com.smartperty.smartperty.tenant.home.rentAndUtilities


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.smartperty.smartperty.R
import com.smartperty.smartperty.tools.TimeUtil
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.tenant_fragment_rent_and_utilities.view.*
import java.util.*

class TenantRentAndUtilitiesFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        root = inflater.inflate(R.layout.tenant_fragment_rent_and_utilities, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        if (GlobalVariables.estate.contract != null) {
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

        return root
    }


}

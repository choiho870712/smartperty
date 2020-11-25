package com.smartperty.smartperty.tenant.home.equipmentManualPdf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables

class TenantEquipmentManualPdfFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        return inflater.inflate(R.layout.tenant_fragment_equipment_manual_pdf, container, false)
    }
}
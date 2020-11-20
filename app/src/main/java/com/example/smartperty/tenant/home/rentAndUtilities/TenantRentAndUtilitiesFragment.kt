package com.example.smartperty.tenant.home.rentAndUtilities


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.smartperty.R
import com.example.smartperty.utils.GlobalVariables

class TenantRentAndUtilitiesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        return inflater.inflate(R.layout.tenant_fragment_rent_and_utilities, container, false)
    }


}

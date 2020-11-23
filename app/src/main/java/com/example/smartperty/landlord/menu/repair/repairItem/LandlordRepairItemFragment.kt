package com.example.smartperty.landlord.menu.repair.repairItem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartperty.R
import com.example.smartperty.utils.GlobalVariables

class LandlordRepairItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        GlobalVariables.toolBarUtils.setTitle("")
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        return inflater.inflate(R.layout.landlord_fragment_repair_item, container, false)
    }

}
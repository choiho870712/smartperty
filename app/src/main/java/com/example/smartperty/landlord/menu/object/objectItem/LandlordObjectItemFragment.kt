package com.example.smartperty.landlord.menu.`object`.objectItem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartperty.R
import com.example.smartperty.landlord.menu.repair.data.LandlordRepairItem
import com.example.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.landlord_fragment_object_item.view.*

class LandlordObjectItemFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.landlord_fragment_object_item, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setEditButtonVisibility(true)

        val imageUrlList = mutableListOf(
            "a", "b", "c"
        )
        root.recycler_image.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false )
            adapter = LandlordObjectItemImageAdapter(requireActivity(), root, imageUrlList)
        }

        val repairList = mutableListOf(
            LandlordRepairItem("水管塞住", "已結案", "2020/11/30", "$10000"),
            LandlordRepairItem("廁所漏水", "已結案", "2020/11/29", "$15000"),
            LandlordRepairItem("牆壁破洞", "已結案", "2020/11/28", "$1000000")
        )

        root.recycler_repair.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = LandlordObjectItemRepairAdapter(requireActivity(), root, repairList)
        }


        return root
    }
}
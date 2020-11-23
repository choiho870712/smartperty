package com.example.smartperty.landlord.menu.repair.repairList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartperty.R
import com.example.smartperty.landlord.menu.repair.data.LandlordRepairItem
import com.example.smartperty.landlord.menu.repair.data.LandlordRepairList
import com.example.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.landlord_fragment_object_list.view.*
import kotlinx.android.synthetic.main.landlord_fragment_repair_list.view.*

class LandlordRepairListFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.landlord_fragment_repair_list, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        val repairList = LandlordRepairList(
            list = mutableListOf(
                LandlordRepairItem(
                    address = "address",
                    content = "content",
                    date = "date"
                )
            )
        )

        root.recycler_repair_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = LandlordRepairListAdapter(requireActivity(), root, repairList.list)
        }

        return root
    }
}
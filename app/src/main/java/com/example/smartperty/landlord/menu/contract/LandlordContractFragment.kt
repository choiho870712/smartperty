package com.example.smartperty.landlord.menu.contract

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartperty.R
import com.example.smartperty.landlord.menu.`object`.data.LandlordObjectItem
import com.example.smartperty.landlord.menu.`object`.data.LandlordObjectList
import kotlinx.android.synthetic.main.landlord_fragment_contract.view.*

class LandlordContractFragment : Fragment() {

    private lateinit var root:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.landlord_fragment_contract, container, false)

        val folderList = LandlordObjectList(
            title = "list1",
            itemList = mutableListOf(
                LandlordObjectItem(title = "item1")
            )
        )

        root.recycler_contract_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = LandlordContractAdapter(requireActivity(), root, folderList.itemList)
        }

        return root
    }
}
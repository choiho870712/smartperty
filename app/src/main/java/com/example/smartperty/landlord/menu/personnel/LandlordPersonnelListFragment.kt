package com.example.smartperty.landlord.menu.personnel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartperty.R
import com.example.smartperty.landlord.menu.personnel.data.LandlordPersonnelItem
import com.example.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.landlord_fragment_object_list.view.*
import kotlinx.android.synthetic.main.landlord_fragment_personnel_list.view.*

class LandlordPersonnelListFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.landlord_fragment_personnel_list, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        var managerList = mutableListOf(
            LandlordPersonnelItem(name = "manager Jason"),
            LandlordPersonnelItem(name = "manager Aiden"),
            LandlordPersonnelItem(name = "manager Hugo")
        )

        var accountantList = mutableListOf(
            LandlordPersonnelItem(name = "accountant Jason"),
            LandlordPersonnelItem(name = "accountant Aiden"),
            LandlordPersonnelItem(name = "accountant Hugo")
        )

        var plumberList = mutableListOf(
            LandlordPersonnelItem(name = "plumber Jason"),
            LandlordPersonnelItem(name = "plumber Aiden"),
            LandlordPersonnelItem(name = "plumber Hugo")
        )

        root.recycler_personnel_manager.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = LandlordPersonnelListAdapter(requireActivity(), root, managerList)
        }

        root.recycler_personnel_accountant.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = LandlordPersonnelListAdapter(requireActivity(), root, accountantList)
        }

        root.recycler_personnel_plumber.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = LandlordPersonnelListAdapter(requireActivity(), root, plumberList)
        }
        return root
    }

}
package com.smartperty.smartperty.landlord.menu.personnel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_personnel_list.view.*

class PersonnelListFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_personnel_list, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        root.recycler_personnel_manager.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = PersonnelListAdapter(requireActivity(), root, GlobalVariables.managerList)
        }

        root.recycler_personnel_accountant.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = PersonnelListAdapter(requireActivity(), root, GlobalVariables.accountantList)
        }

        root.recycler_personnel_plumber.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = PersonnelListAdapter(requireActivity(), root, GlobalVariables.plumberList)
        }
        return root
    }

}
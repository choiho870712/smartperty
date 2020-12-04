package com.smartperty.smartperty.landlord.menu.`object`.estateList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.fragment_estate_list.view.*

class EstateListFragment : Fragment() {

    private lateinit var root:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_estate_list, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setAddButtonVisibility(true)
        GlobalVariables.toolBarUtils.setTitle(GlobalVariables.estateList.title)

        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.button_add -> {
                    root.findNavController().navigate(
                        R.id.action_landlordObjectListFragment_to_landlordObjectListAddFragment)

                    true
                }
                else -> false
            }
        }

        GlobalVariables.estateListLayoutManager = LinearLayoutManager(activity)
        GlobalVariables.estateListAdapter =
            EstateListAdapter(
                requireActivity(), root,
                GlobalVariables.estateList.list
            )

        root.recycler_object_list.apply {
            setHasFixedSize(true)
            layoutManager = GlobalVariables.estateListLayoutManager
            adapter = GlobalVariables.estateListAdapter
        }

        return root
    }
}
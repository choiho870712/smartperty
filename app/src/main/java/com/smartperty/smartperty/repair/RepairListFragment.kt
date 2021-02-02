package com.smartperty.smartperty.repair

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.RepairOrder
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.fragment_recycler.view.*

class RepairListFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_recycler, container, false)

        setToolBar()
        createRepairListView()

        return root
    }

    private fun setToolBar() {
        GlobalVariables.toolBarUtils.setVisibility(true)
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        if (GlobalVariables.loginUser.permission.event == "A")
            GlobalVariables.toolBarUtils.setAddButtonVisibility(true)

        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.button_add -> {
                    // setup dialog builder
                    val builder = android.app.AlertDialog.Builder(requireActivity())
                    builder.setTitle("是否新增報修單？")

                    builder.setPositiveButton("是") { _, _ ->
                        root.findNavController().navigate(
                            R.id.action_repairListFragment_to_repairOrderCreateFragment
                        )
                    }

                    // create dialog and show it
                    requireActivity().runOnUiThread{
                        val dialog = builder.create()
                        dialog.show()
                    }

                    true
                }
                else -> false
            }
        }
    }

    private fun createRepairListView() {
        GlobalVariables.repairListLayoutManager = LinearLayoutManager(activity)
        if (GlobalVariables.loginUser.auth == "tenant") {
            GlobalVariables.loginUser.repairList = GlobalVariables.loginUser.estate.repairList
        }
        else if (GlobalVariables.loginUser.auth == "technician") {
            GlobalVariables.loginUser.repairList = mutableListOf()
            GlobalVariables.loginUser.rootUser!!.repairList.forEach {
                val me = it.participant.find { it.id == GlobalVariables.loginUser.id }
                if (me != null) {
                    GlobalVariables.loginUser.repairList.add(it)
                }
            }
        }
        GlobalVariables.repairListAdapter = RepairListAdapter(
            requireActivity(), root,
            GlobalVariables.loginUser.repairList
        )
        root.recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = GlobalVariables.repairListLayoutManager
            adapter = GlobalVariables.repairListAdapter
        }
    }
}
package com.smartperty.smartperty.tenant.home.equipmentManual


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.Equipment
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.tenant_fragment_equipment_manual.view.*

class TenantEquipmentManualFragment : Fragment() {

    lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.tenant_fragment_equipment_manual, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        if (GlobalVariables.loginUser.auth == "landlord") {
            GlobalVariables.toolBarUtils.setAddButtonVisibility(true)
            GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.button_add -> {
                        // setup dialog builder
                        val builder = android.app.AlertDialog.Builder(requireActivity())
                        builder.setTitle("確定要新增嗎？")

                        builder.setPositiveButton("是") { _, _ ->
                            root.findNavController().navigate(
                                R.id.action_tenantEquipmentManualFragment2_to_equipmentAddFragment
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


        val equipmentManual = GlobalVariables.estate.roomList

        val itemList = mutableListOf<Equipment>()
        equipmentManual.forEach{
            itemList.addAll(itemList.size, it.equipmentList)
        }
        val equipmentAdapter = TenantEquipmentAdapter(requireActivity(), root, itemList)

        val sections = ArrayList<TenantEquipmentSectionedAdapter.Section>()
        var count = 0
        equipmentManual.forEach {
            sections.add(TenantEquipmentSectionedAdapter.Section(count, it.name))
            count += it.equipmentList.size
        }

        val dummy = arrayOfNulls<TenantEquipmentSectionedAdapter.Section>(sections.size)
        val sectionedListAdapter = TenantEquipmentSectionedAdapter(equipmentAdapter)
        sectionedListAdapter.setSections(sections.toArray(dummy))

        root.recycler_equipment_manual.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = sectionedListAdapter
        }

        return root
    }


}

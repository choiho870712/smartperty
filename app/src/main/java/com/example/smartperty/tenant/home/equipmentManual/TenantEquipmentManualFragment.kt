package com.example.smartperty.tenant.home.equipmentManual


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartperty.R
import com.example.smartperty.tenant.home.equipmentManual.data.TenantEquipmentManual
import com.example.smartperty.utils.GlobalVariables.Companion.toolBarUtils
import kotlinx.android.synthetic.main.tenant_fragment_equipment_manual.view.*


/**
 * A simple [Fragment] subclass.
 */
class TenantEquipmentManualFragment : Fragment() {

    lateinit var root: View
//    private lateinit var equipmentManualViewModel: EquipmentManualViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.tenant_fragment_equipment_manual, container, false)

        toolBarUtils.removeAllButtonAndLogo()

        val equipmentManual = TenantEquipmentManual()
        equipmentManual.loadData()

        val equipmentAdapter = TenantEquipmentAdapter(requireActivity(), root, equipmentManual.getItemList())

        val sections = ArrayList<TenantEquipmentSectionedAdapter.Section>()
        var count = 0
        equipmentManual.manual.forEach {
            sections.add(TenantEquipmentSectionedAdapter.Section(count, it.room))
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

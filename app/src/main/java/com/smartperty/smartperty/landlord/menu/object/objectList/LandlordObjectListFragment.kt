package com.smartperty.smartperty.landlord.menu.`object`.objectList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.landlord.menu.`object`.data.LandlordObjectItem
import com.smartperty.smartperty.landlord.menu.`object`.data.LandlordObjectList
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.landlord_fragment_object_list.view.*

class LandlordObjectListFragment : Fragment() {

    private lateinit var root:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.landlord_fragment_object_list, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        val folderList = LandlordObjectList(
            title = "list1",
            itemList = mutableListOf(
                LandlordObjectItem(title = "item1"),
                LandlordObjectItem(title = "item2")
            )
        )

        root.recycler_object_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = LandlordObjectListAdapter(requireActivity(), root, folderList.itemList)
        }

        return root
    }
}
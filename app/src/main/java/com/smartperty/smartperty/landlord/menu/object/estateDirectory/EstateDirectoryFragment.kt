package com.smartperty.smartperty.landlord.menu.`object`.estateDirectory

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
import kotlinx.android.synthetic.main.fragment_estate_directory.view.*


class EstateDirectoryFragment : Fragment() {

    private lateinit var root:View
    private var lockRefresh = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_estate_directory, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setAddButtonVisibility(true)

        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.button_add -> {
                    root.findNavController().navigate(
                        R.id.action_estateDirectoryFragment_to_estateDirectoryCreateFragment)

                    true
                }
                else -> false
            }
        }

        GlobalVariables.estateDirectoryLayoutManager = LinearLayoutManager(activity)
        GlobalVariables.estateDirectoryAdapter =
            EstateDirectoryAdapter(
                requireActivity(), root,
                GlobalVariables.estateDirectory
            )

        root.recycler_object_folder.apply {
            setHasFixedSize(true)
            layoutManager = GlobalVariables.estateDirectoryLayoutManager
            adapter = GlobalVariables.estateDirectoryAdapter
        }

        linkRefreshListener()

        root.card_image_large.setOnClickListener {
            root.card_image_large.visibility = View.GONE
        }

        return root
    }

    private fun linkRefreshListener() {
        root.swipe_layout.setOnRefreshListener {
            if (!lockRefresh) {
                lockRefresh = true

                GlobalVariables.estateDirectory.forEach {
                    it.image = null
                }
                GlobalVariables.estateDirectory.clear()
                GlobalVariables.estateDirectoryAdapter!!.notifyDataSetChanged()

                Thread {
                    GlobalVariables.api.getGroupTag(GlobalVariables.user.id)
                    if (activity != null) requireActivity().runOnUiThread {
                        GlobalVariables.estateDirectoryAdapter!!.notifyDataSetChanged()
                        lockRefresh = false
                        root.swipe_layout.isRefreshing = false
                    }
                }.start()
            }
        }
    }
}
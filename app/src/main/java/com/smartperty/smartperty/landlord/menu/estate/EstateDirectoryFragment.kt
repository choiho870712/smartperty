package com.smartperty.smartperty.landlord.menu.estate

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.igreenwood.loupe.Loupe
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.EstateList
import com.smartperty.smartperty.tools.SwipeHelper
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.fragment_estate_directory.view.*
import kotlinx.android.synthetic.main.fragment_estate_directory_create.view.*


class EstateDirectoryFragment : Fragment() {

    private lateinit var root:View
    private var lockRefresh = false

    @RequiresApi(Build.VERSION_CODES.M)
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
                    GlobalVariables.estateFolder = EstateList()
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

        var swipeHelper = object :SwipeHelper(requireContext(), root.recycler_object_folder) {
            override fun instantiateUnderlayButton(
                viewHolder: RecyclerView.ViewHolder?,
                underlayButtons: MutableList<UnderlayButton>?
            ) {
                underlayButtons?.add(UnderlayButton(
                    "Delete",
                    0,
                    Color.parseColor("#FF3C30")
                ) {
                    if (viewHolder != null) {
                        val index = viewHolder.adapterPosition
                        GlobalVariables.estateFolder =
                            GlobalVariables.estateDirectory[index]
                        val url = GlobalVariables.estateFolder.imageUrl

                        // setup dialog builder
                        val builder = android.app.AlertDialog.Builder(requireActivity())

                        if (GlobalVariables.estateFolder.list.isEmpty()) {
                            builder.setTitle("確定要刪除嗎？")
                            builder.setPositiveButton("是") { _, _ ->
                                Thread {
                                    GlobalVariables.api.deleteGroupTag(
                                        GlobalVariables.loginUser.id,
                                        index,
                                        url
                                    )
                                }.start()
                                GlobalVariables.estateDirectory.remove(GlobalVariables.estateFolder)
                                GlobalVariables.estateDirectoryAdapter!!.notifyDataSetChanged()
                            }
                        } else {
                            builder.setTitle("不可刪除已有物件的群組")
                            builder.setPositiveButton("是") { _, _ -> }
                        }

                        // create dialog and show it
                        requireActivity().runOnUiThread {
                            val dialog = builder.create()
                            dialog.show()
                        }
                    }
                })
//                underlayButtons?.add(UnderlayButton(
//                    "Edit",
//                    0,
//                    Color.parseColor("#FF9502")
//                ) {
//                    if (viewHolder != null) {
//                        GlobalVariables.estateFolder =
//                            GlobalVariables.estateDirectory[viewHolder.adapterPosition]
//                        GlobalVariables.estateFolder.groupIndex = viewHolder.adapterPosition
//                    }
//                    root.findNavController().navigate(
//                        R.id.action_estateDirectoryFragment_to_estateDirectoryCreateFragment)
//                })
//                underlayButtons?.add(UnderlayButton(
//                    "Cancel",
//                    0,
//                    Color.parseColor("#C7C7CB")
//                ) {

//                })
            }
        }


        //linkRefreshListener()

        root.card_image_large.setOnClickListener {
            root.card_image_large.visibility = View.GONE
        }

        return root
    }

//    private fun linkRefreshListener() {
//        root.swipe_layout.setOnRefreshListener {
//            if (!lockRefresh) {
//                lockRefresh = true
//
//                GlobalVariables.estateDirectory.forEach {
//                    it.image = null
//                }
//                GlobalVariables.estateDirectory.clear()
//                GlobalVariables.estateDirectoryAdapter!!.notifyDataSetChanged()
//
//                Thread {
//                    GlobalVariables.api.getGroupTag(GlobalVariables.user.id)
//                    if (activity != null) requireActivity().runOnUiThread {
//                        GlobalVariables.estateDirectoryAdapter!!.notifyDataSetChanged()
//                        lockRefresh = false
//                        root.swipe_layout.isRefreshing = false
//                    }
//                }.start()
//            }
//        }
//    }

}


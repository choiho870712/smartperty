package com.smartperty.smartperty.repair

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import kotlinx.android.synthetic.main.fragment_choose_plumber.view.*
import java.util.*


class ChoosePlumberFragment : Fragment() {

    private lateinit var root: View
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var timePickerDialog: TimePickerDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_choose_plumber, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)
        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.button_submit -> {
                    // setup dialog builder
                    val builder = android.app.AlertDialog.Builder(requireActivity())
                    builder.setTitle("確定維修人員？")

                    builder.setPositiveButton("是") { _, _ ->
                        GlobalVariables.repairOrder.participant.add(
                            GlobalVariables.plumber
                        )

                        Thread {
                            GlobalVariables.api.updateEventParticipant(
                                GlobalVariables.repairOrder.landlord!!.id,
                                GlobalVariables.repairOrder.event_id,
                                GlobalVariables.plumber
                            )
                        }.start()

                        root.findNavController().navigateUp()
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

        root.recycler_plumber_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = PlumberAdapter(requireActivity(), root,
                GlobalVariables.loginUser.technicianList)
        }

        root.textView_choose_plumber_name.text = GlobalVariables.plumber.name
        root.textView_choose_plumber_cell_phone.text = GlobalVariables.plumber.cellPhone

        return root
    }

}
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
                        val dateTimeString = "" +
                            root.textView_choose_plumber_date.text +
                            " " +
                            root.textView_choose_plumber_time.text
                        GlobalVariables.repairOrder.repairDateTime = dateTimeString
                        GlobalVariables.repairOrder.plumber = GlobalVariables.plumber
                        GlobalVariables.repairOrder.nextStatus()
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
                GlobalVariables.plumberList)
        }

        root.textView_choose_plumber_name.text = GlobalVariables.plumber.name
        root.textView_choose_plumber_cell_phone.text = GlobalVariables.plumber.cellPhone
        root.textView_choose_plumber_company.text = GlobalVariables.plumber.company

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, y, m, d ->
                val dateString = "$y/$m/$d"
                root.textView_choose_plumber_date.text = dateString
            },
            year, month, day
        )

        root.textView_choose_plumber_date.setOnClickListener {
            datePickerDialog.show()
        }

        timePickerDialog = TimePickerDialog(
            requireContext(),
            {
                _, h, m ->
                val timeString = "$h:$m"
                root.textView_choose_plumber_time.text = timeString
            },
            hour, minute, true
        )

        root.textView_choose_plumber_time.setOnClickListener {
            timePickerDialog.show()
        }

        return root
    }

}
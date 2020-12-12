package com.smartperty.smartperty.landlord.menu.personnel

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.User
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.card_personnel_list.view.*


class PersonnelListAdapter(
    private val activity: Activity,
    private val parentView: View,
    private val myDataset: MutableList<User>
)
    : RecyclerView.Adapter<PersonnelListAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_personnel_list, parent, false
        )

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.index = position
        holder.name.text = myDataset[position].name

        holder.cardView.setOnClickListener {
            GlobalVariables.personnel = myDataset[holder.index]
            parentView.findNavController().navigate(
                R.id.action_personnelListFragment_to_personnelFragment
            )
        }

        holder.controlButton.setOnClickListener {
            val builderSingle: AlertDialog.Builder = AlertDialog.Builder(activity)

            val arrayAdapter = ArrayAdapter<String>(
                activity,
                android.R.layout.select_dialog_singlechoice
            )
            arrayAdapter.add("編輯權限")
            arrayAdapter.add("刪除")

            builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }

            builderSingle.setAdapter(arrayAdapter) { _, which ->
                val strName = arrayAdapter.getItem(which)
                val builderInner: AlertDialog.Builder = AlertDialog.Builder(activity)
                when (strName) {
                    "編輯權限" -> {
                        builderInner.setTitle("確定編輯權限？")
                        builderInner.setPositiveButton("是") { dialog, which ->
                            dialog.dismiss()
                        }
                        builderInner.setNegativeButton("否"
                        ) { dialog, which -> dialog.dismiss() }
                        builderInner.show()
                    }
                    "刪除" -> {
                        builderInner.setTitle("確定刪除？")
                        builderInner.setPositiveButton("是") { dialog, which ->
                            removeItem(holder.index)
                            dialog.dismiss()
                        }
                        builderInner.setNegativeButton("否"
                        ) { dialog, which -> dialog.dismiss() }
                        builderInner.show()
                    }
                    else -> {}
                }
            }
            builderSingle.show()
        }
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        var index: Int = -1
        val name: TextView = card.text_personnel_list_name
        val controlButton: ImageButton = card.button_personnel_control
    }

    fun removeItem(position: Int) {
        myDataset.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: User, position: Int) {
        myDataset.add(position, item)
        notifyItemInserted(position)
    }

    fun getData(): MutableList<User> {
        return myDataset
    }
}
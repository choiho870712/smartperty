package com.smartperty.smartperty.repair

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.RepairOrder
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.card_repair_list2.view.*

class RepairList2Adapter(private val activity: Activity,
                         private val parentView: View,
                         private val myDataset: MutableList<RepairOrder>)
    : RecyclerView.Adapter<RepairList2Adapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_repair_list2, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.index = position
        holder.title.text = myDataset[position].description
        holder.date.text = myDataset[position].date
        holder.cost.text = myDataset[position].cost.toString()

        val myText = when(myDataset[position].status) {
            "Received" -> {
                "已接案"
            }
            "Assigned" -> {
                "已指派"
            }
            "Processing" -> {
                "處理中"
            }
            "Closed" -> {
                "已結案"
            }
            else -> {
                "選擇狀態"
            }
        }
        holder.status.text = myText

        holder.cardView.setOnClickListener {
            GlobalVariables.repairOrder = myDataset[holder.index]
            if (parentView.findNavController().currentDestination?.id == R.id.repairListFragment)
                parentView.findNavController().navigate(
                    R.id.action_repairListFragment_to_repairOrderFragment)
            if (parentView.findNavController().currentDestination?.id == R.id.estateFragment)
                parentView.findNavController().navigate(
                    R.id.action_estateFragment_to_repairOrderFragment)

        }

    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        var index: Int = -1
        val title: TextView = card.text_repair_list_title
        val date: TextView = card.text_repair_list_date
        val status: TextView = card.text_repair_list_status
        val cost: TextView = card.text_repair_list_cost
    }
}
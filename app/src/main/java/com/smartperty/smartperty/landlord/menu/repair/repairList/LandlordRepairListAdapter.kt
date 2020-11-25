package com.smartperty.smartperty.landlord.menu.repair.repairList

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.landlord.menu.repair.data.LandlordRepairItem
import kotlinx.android.synthetic.main.landlord_card_repair_list.view.*

class LandlordRepairListAdapter(private val activity: Activity,
                                private val parentView: View,
                                private val myDataset: MutableList<LandlordRepairItem>)
    : RecyclerView.Adapter<LandlordRepairListAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.landlord_card_repair_list, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.address.text = myDataset[position].address
        holder.content.text = myDataset[position].content
        holder.date.text = myDataset[position].date

        holder.cardView.setOnClickListener {
            parentView.findNavController().navigate(
                R.id.action_landlordRepairListFragment_to_landlordRepairItemFragment)
        }

    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        val address: TextView = card.text_repair_list_address
        val content: TextView = card.text_repair_list_content
        var date: TextView = card.text_repair_list_date
    }
}
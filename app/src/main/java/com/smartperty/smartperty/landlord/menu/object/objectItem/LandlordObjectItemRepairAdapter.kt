package com.smartperty.smartperty.landlord.menu.`object`.objectItem

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.landlord.menu.repair.data.LandlordRepairItem
import kotlinx.android.synthetic.main.landlord_card_object_item_repair.view.*

class LandlordObjectItemRepairAdapter(private val activity: Activity,
                                      private val parentView: View,
                                      private val myDataset: MutableList<LandlordRepairItem>)
    : RecyclerView.Adapter<LandlordObjectItemRepairAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.landlord_card_object_item_repair, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.title.text = myDataset[position].title
        holder.status.text = myDataset[position].status
        holder.date.text = myDataset[position].date
        holder.spend.text = myDataset[position].spend
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        val title = card.text_repair_title
        val status = card.text_repair_status
        val date = card.text_repair_date
        val spend = card.text_repair_spend
    }
}
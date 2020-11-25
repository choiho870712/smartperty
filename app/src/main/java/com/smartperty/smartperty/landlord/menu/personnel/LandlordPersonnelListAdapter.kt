package com.smartperty.smartperty.landlord.menu.personnel

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.landlord.menu.personnel.data.LandlordPersonnelItem
import kotlinx.android.synthetic.main.landlord_card_personnel_list.view.*

class LandlordPersonnelListAdapter(private val activity: Activity,
                                   private val parentView: View,
                                   private val myDataset: MutableList<LandlordPersonnelItem>)
    : RecyclerView.Adapter<LandlordPersonnelListAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.landlord_card_personnel_list, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.name.text = myDataset[position].name
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        val name: TextView = card.text_personnel_list_name
    }
}
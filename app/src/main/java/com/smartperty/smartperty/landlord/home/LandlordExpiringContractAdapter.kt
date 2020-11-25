package com.smartperty.smartperty.landlord.home

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.landlord.home.data.LandlordExpiringContract
import kotlinx.android.synthetic.main.landlord_card_expiring_contract.view.*

class LandlordExpiringContractAdapter(private val activity: Activity,
                                      private val parentView: View,
                                      private val myDataset: MutableList<LandlordExpiringContract>)
    : RecyclerView.Adapter<LandlordExpiringContractAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.landlord_card_expiring_contract, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.title.text = myDataset[position].title
        holder.address.text = myDataset[position].address
        holder.expireTime.text = myDataset[position].expireMonth
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        val title: TextView = card.text_title
        val address: TextView = card.text_address
        val expireTime:TextView = card.text_expireTime
    }
}
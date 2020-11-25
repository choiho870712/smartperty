package com.smartperty.smartperty.landlord.home

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.landlord.home.data.LandlordExpiringRent
import kotlinx.android.synthetic.main.landlord_card_expiring_rent.view.*

class LandlordExpiringRentAdapter(private val activity: Activity,
                                  private val parentView: View,
                                  private val myDataset: MutableList<LandlordExpiringRent>)
    : RecyclerView.Adapter<LandlordExpiringRentAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.landlord_card_expiring_rent, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.title.text = myDataset[position].title
        holder.tenantName.text = myDataset[position].tenantName
        holder.expireTime.text = myDataset[position].expireMonth
        holder.amount.text = myDataset[position].rentAmount
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        val title: TextView = card.text_title
        val tenantName:TextView = card.text_tenantName
        val expireTime:TextView = card.text_expireTime
        val amount:TextView = card.text_amount
    }
}
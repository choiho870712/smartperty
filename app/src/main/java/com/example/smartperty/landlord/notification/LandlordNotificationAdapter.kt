package com.example.smartperty.landlord.notification

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartperty.R
import com.example.smartperty.landlord.notification.data.LandlordNotification
import kotlinx.android.synthetic.main.tenant_card_notification.view.*

class LandlordNotificationAdapter(private val activity: Activity,
                                  private val parentView: View,
                                  private val myDataset: MutableList<LandlordNotification>)
    : RecyclerView.Adapter<LandlordNotificationAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.landlord_card_notification, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.message.text = myDataset[position].message
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        val message: TextView = card.text_message
    }
}
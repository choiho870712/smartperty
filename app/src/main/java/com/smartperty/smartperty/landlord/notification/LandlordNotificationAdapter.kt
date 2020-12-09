package com.smartperty.smartperty.landlord.notification

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.Notification
import kotlinx.android.synthetic.main.card_notification.view.*
import kotlinx.android.synthetic.main.tenant_card_notification.view.text_message

class LandlordNotificationAdapter(private val activity: Activity,
                                  private val parentView: View,
                                  private val myDataset: MutableList<Notification>)
    : RecyclerView.Adapter<LandlordNotificationAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_notification, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.message.text = myDataset[position].message
        holder.date.text = myDataset[position].date
        holder.notificationColor.setImageDrawable(myDataset[position].getDrawable())
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        val message: TextView = card.text_message
        val date: TextView = card.text_date
        val notificationColor: ImageView = card.imageView_notification_color
    }
}
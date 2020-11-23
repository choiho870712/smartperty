package com.example.smartperty.landlord.chat

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartperty.R
import com.example.smartperty.landlord.chat.data.LandlordChat
import kotlinx.android.synthetic.main.landlord_card_chat.view.*

class LandlordChatAdapter(private val activity: Activity,
                          private val parentView: View,
                          private val myDataset: MutableList<LandlordChat>)
    : RecyclerView.Adapter<LandlordChatAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.landlord_card_chat, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.message.text = myDataset[position].message
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        val name: TextView = card.text_name
        val message: TextView = card.text_message
    }
}
package com.smartperty.smartperty.landlord.menu.finance

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.FinanceItem
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.card_finance_item.view.*

class FinanceAdapter(private val activity: Activity,
                     private val parentView: View,
                     private val myDataset: MutableList<FinanceItem>)
    : RecyclerView.Adapter<FinanceAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_finance_item, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.title.text = myDataset[position].name
        holder.value.text = myDataset[position].value.toString()
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        val title: TextView = card.text_finance_item_title
        val value: TextView = card.text_finance_item_value
    }
}
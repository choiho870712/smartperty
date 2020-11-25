package com.smartperty.smartperty.landlord.menu.`object`.objectList

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.landlord.menu.`object`.data.LandlordObjectItem
import kotlinx.android.synthetic.main.landlord_card_object_list.view.*

class LandlordObjectListAdapter(private val activity: Activity,
                                private val parentView: View,
                                private val myDataset: MutableList<LandlordObjectItem>)
    : RecyclerView.Adapter<LandlordObjectListAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.landlord_card_object_list, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.title.text = myDataset[position].title

        holder.cardView.setOnClickListener {
            parentView.findNavController().navigate(
                R.id.action_landlordObjectListFragment_to_landlordObjectItemFragment)
        }
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        val title: TextView = card.text_object_list_title
    }
}
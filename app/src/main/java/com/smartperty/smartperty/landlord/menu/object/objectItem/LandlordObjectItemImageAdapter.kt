package com.smartperty.smartperty.landlord.menu.`object`.objectItem

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R

class LandlordObjectItemImageAdapter(private val activity: Activity,
                                     private val parentView: View,
                                     private val myDataset: MutableList<String>)
    : RecyclerView.Adapter<LandlordObjectItemImageAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.landlord_card_object_item_image, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {

    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
    }
}
package com.example.smartperty.landlord.menu.`object`.objectItem

import android.app.Activity
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.smartperty.R
import com.example.smartperty.landlord.menu.`object`.data.LandlordObjectList
import kotlinx.android.synthetic.main.landlord_card_object_folder.view.*

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
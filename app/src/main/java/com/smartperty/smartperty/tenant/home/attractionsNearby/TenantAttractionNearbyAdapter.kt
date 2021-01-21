package com.smartperty.smartperty.tenant.home.attractionsNearby

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.tenant.home.attractionsNearby.data.AttractionNearbyItem
import kotlinx.android.synthetic.main.tenant_card_attraction_nearby.view.*

class TenantAttractionNearbyAdapter(private val activity: Activity,
                                    private val parentView: View,
                                    private val myDataset: MutableList<AttractionNearbyItem>)
    : RecyclerView.Adapter<TenantAttractionNearbyAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.tenant_card_attraction_nearby, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.name.text = myDataset[position].name
        holder.address.text =
            myDataset[position].region + myDataset[position].road +
                    myDataset[position].street + myDataset[position].address
        holder.description.text = myDataset[position].description
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        val name: TextView = card.text_nearby_name
        val address: TextView = card.text_nearby_address
        val description: TextView = card.text_nearby_description

    }
}
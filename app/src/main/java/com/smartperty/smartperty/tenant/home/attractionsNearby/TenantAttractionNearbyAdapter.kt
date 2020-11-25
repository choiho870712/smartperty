package com.smartperty.smartperty.tenant.home.attractionsNearby

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.tenant.home.attractionsNearby.data.TenantAttractionNearbyItem
import kotlinx.android.synthetic.main.tenant_card_attraction_nearby.view.*

class TenantAttractionNearbyAdapter(private val activity: Activity,
                                    private val parentView: View,
                                    private val myDataset: MutableList<TenantAttractionNearbyItem>)
    : RecyclerView.Adapter<TenantAttractionNearbyAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.tenant_card_attraction_nearby, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.name.text = myDataset[position].name
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        val name: TextView = card.text_name
    }
}
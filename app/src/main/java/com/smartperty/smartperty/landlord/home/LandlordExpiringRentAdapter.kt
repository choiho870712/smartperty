package com.smartperty.smartperty.landlord.home

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.Contract
import com.smartperty.smartperty.landlord.home.data.LandlordExpiringRent
import com.smartperty.smartperty.tools.TimeUtil
import com.smartperty.smartperty.utils.GlobalVariables
import com.smartperty.smartperty.utils.Utils
import kotlinx.android.synthetic.main.landlord_card_expiring_rent.view.*
import java.util.*

class LandlordExpiringRentAdapter(private val activity: Activity,
                                  private val parentView: View,
                                  private val myDataset: MutableList<Contract>)
    : RecyclerView.Adapter<LandlordExpiringRentAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.landlord_card_expiring_rent, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.title.text = myDataset[position].estate!!.objectName
        holder.tenantName.text = myDataset[position].tenant!!.name
        holder.expireTime.text = TimeUtil.StampToDate(myDataset[position].getNextDate(), Locale.TAIWAN)
        holder.amount.text = myDataset[position].rent.toString()

        holder.cardView.setOnClickListener {
            GlobalVariables.estate = myDataset[position].estate!!
            val uri = Uri.parse("android-app://com.smartperty.smartperty/estateFragment")
            parentView.findNavController().navigate(uri)
        }
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        val title: TextView = card.text_title
        val tenantName:TextView = card.text_tenantName
        val expireTime:TextView = card.text_expireTime
        val amount:TextView = card.text_amount
    }
}
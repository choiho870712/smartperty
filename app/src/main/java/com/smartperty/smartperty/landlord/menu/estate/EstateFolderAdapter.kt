package com.smartperty.smartperty.landlord.menu.estate

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.Estate
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.card_estate.view.*

class EstateFolderAdapter(private val activity: Activity,
                          private val parentView: View,
                          private val myDataset: MutableList<Estate>)
    : RecyclerView.Adapter<EstateFolderAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_estate, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.index = position
        holder.title.text = myDataset[position].objectName
        holder.address.text = myDataset[position].fullAddress
        holder.tenantName.text = myDataset[position].tenant?.name ?: ""
        holder.rentAmount.text = myDataset[position].rent.toString()
        holder.squareFt.text = myDataset[position].area.toString()

        holder.selectButton.visibility = View.GONE

        if (myDataset[position].imageList.isNotEmpty())
            holder.image.setImageBitmap(myDataset[position].imageList[0])
        else
            holder.image.setImageDrawable(
                activity.resources.getDrawable(R.drawable.empty_house))

        holder.cardView.setOnClickListener {
            GlobalVariables.estate = myDataset[holder.index]
            parentView.findNavController().navigate(
                R.id.action_landlordObjectListFragment_to_landlordObjectItemFragment)
        }
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        var index: Int = -1
        val title: TextView = card.text_object_list_title
        val address: TextView = card.text_object_list_address
        val tenantName: TextView = card.text_object_list_tenant_name
        val rentAmount: TextView = card.text_object_list_rent
        var squareFt: TextView = card.text_object_list_square_ft
        val image: ImageView = card.image_object_list
        val selectButton: Button = card.button_choose_tenant
    }
}